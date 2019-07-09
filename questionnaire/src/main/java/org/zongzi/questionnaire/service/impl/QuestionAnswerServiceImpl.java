package org.zongzi.questionnaire.service.impl;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.common.utils.ExampleUtils;
import org.zongzi.platform.common.utils.IDUtils;
import org.zongzi.platform.common.utils.SqlUtils;
import org.zongzi.questionnaire.common.ParameterMapUtils;
import org.zongzi.questionnaire.domain.*;
import org.zongzi.questionnaire.mapper.*;
import org.zongzi.questionnaire.service.QuestionAnswerService;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author F.S.Zhang
 * @date 2018/1/30
 * @copyright Hanboard
 * Created by F.S.Zhang on 2018/1/30.
 * 问卷答案和报告-业务逻辑-实现类
 */
@Slf4j
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Autowired
    private QuestionnaireRecordMapper questionnaireRecordMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionRecordMapper questionRecordMapper;
    @Autowired
    private OptionRecordMapper optionRecordMapper;
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Autowired
    private OptionMapper optionMapper;

    @Override
    public ApiResponse selectAll(Map<String, String> filter) {
        Map<String, Object> filterMap = ParameterMapUtils.convertParameter(filter);
        List<Questionnaire> questionnaires = questionnaireMapper.findAll(filterMap);
        return ApiResponse.success(questionnaires);
    }

    /**
     * 按照Id查询试卷答案
     *
     * @param id     问卷id
     * @param userId 用户id
     * @return 试卷答案
     */
    @Override
    public ApiResponse selectAnsById(String id, String userId) {
        // 根据问卷ID和用户ID查询问卷信息
        Questionnaire questionnaire = null;
        List<Questionnaire> questionnaires = questionnaireMapper.findAll(ImmutableMap.of("id", id, "operatorId", userId));
        if (CollectionUtils.isEmpty(questionnaires)) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        questionnaire = questionnaires.get(0);

        // 判断该用户是否参与了该问卷调查
        Integer isAnswer = (Integer) questionnaire.get("isAnswer");
        if (isAnswer == 1) {
            // 获取问卷记录ID
            String questionRecordId = (String) questionnaire.get("questionRecordId");
            // 根据问卷记录ID查询出所有的评分题和解答题答案，并转换为 问题ID->答案 的Map
            Weekend<QuestionRecord> qtrcExample = ExampleUtils.example(QuestionRecord.class,
                    o -> o.andEqualTo(QuestionRecord::getQnRcId, questionRecordId));
            Map<String, QuestionRecord> qtrcMap = questionRecordMapper.selectByExample(qtrcExample)
                    .stream().collect(Collectors.toMap(QuestionRecord::getQtId, Function.identity()));

            // 根据问卷记录ID查询出所有的单选、多选和量表题答案，并转换为 选项ID->选项 的Map
            Weekend<OptionRecord> qorcExample = ExampleUtils.example(OptionRecord.class,
                    o -> o.andEqualTo(OptionRecord::getQnRcId, questionRecordId));
            Map<String, OptionRecord> qorcMap = optionRecordMapper.selectByExample(qorcExample)
                    .stream().collect(Collectors.toMap(OptionRecord::getOpId, o -> o));

            // 查询出该问卷的所有问题
            List<Question> questions = questionMapper.selectByQnId(id);
            // 查询该问卷的所有单选、量表、多选题的选项
            List<Option> questionOptions = null;
            Set<String> qtIdSet = questions.stream().map(Question::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(qtIdSet)) {
                Weekend<Option> qtotExample = ExampleUtils.example(Option.class,
                        o -> o.andIn(Option::getQtId, qtIdSet));
                questionOptions = optionMapper.selectByExample(qtotExample);
            }
            // 将问题选项转换为 问题ID->选项集 的Map，变量 questionOptions 不在使用；
            Map<String, List<Option>> qtotMap = Collections.emptyMap();
            if (!CollectionUtils.isEmpty(questionOptions)) {
                qtotMap = questionOptions.stream().collect(Collectors.groupingBy(Option::getQtId));
            }

            // 遍历该问卷的所有问题，
            for (Question question : questions) {
                List<OptionRecord> optionRecords = null;
                // 单选、量表
                if (question.getType() == 0 || question.getType() == 1) {
                    questionOptions = qtotMap.get(question.getId());
                    question.setExtraMap("options", questionOptions);
                    question.setExtraMap("radio", "");
                    questionOptions.stream()
                            .filter(o -> qorcMap.containsKey(o.getId()))
                            .findFirst().ifPresent(questionOption -> question.setExtraMap("radio", questionOption.getId()));
                }
                // 多选
                if (question.getType() == 1) {
                    questionOptions = qtotMap.get(question.getId());
                    question.setExtraMap("options", questionOptions);

                    Object[] options = questionOptions.stream()
                            .filter(o -> qorcMap.containsKey(o.getId()))
                            .map(Option::getId).toArray();
                    question.setExtraMap("checked", options);
                }
                // 评分
                if (question.getType() == 3) {
                    QuestionRecord questionRecord = qtrcMap.get(question.getId());
                    if (questionRecord != null) {
                        question.setExtraMap("score", questionRecord.getGrade());
                    }
                }
                // 问答
                if (question.getType() == 4) {
                    QuestionRecord questionRecord = qtrcMap.get(question.getId());
                    if (questionRecord != null) {
                        question.setExtraMap("text", questionRecord.getText());
                    }
                }
            }
            questionnaire.setExtraMap("questions", questions);

        }

        return ApiResponse.success(questionnaire);
    }


    // { qtId: 问题ID, radio: 单选题答案，选项ID, checked: 多选题答案，选项ID集合, score: 评分题答案，分数, text: 解答题答案}
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResponse save(String id, String operatorId, List<Map<String, Object>> questions) {
        Questionnaire questionnaire = null;
        List<Questionnaire> questionnaires = questionnaireMapper.findAll(ImmutableMap.of("id", id, "operatorId", operatorId));
        if (CollectionUtils.isEmpty(questionnaires)) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        questionnaire = questionnaires.get(0);
        Integer isAnswer = (Integer) questionnaire.get("isAnswer");
        if (isAnswer == 1) {
            return ApiResponse.error("您已回答过该答卷！");
        }

        QuestionnaireRecord questionnaireRecord = QuestionnaireRecord.builder()
                .id(IDUtils.id()).qnId(id).operatorId(operatorId).operateTime(new Date()).build();

        String qtId = questions.stream().map(o -> Objects.toString(o.get("qtId"), null))
                .filter(Objects::nonNull).collect(Collectors.joining(","));
        List<Question> dbQuestions = questionMapper.selectByIds(qtId);

        Set<QuestionRecord> questionRecords = new LinkedHashSet<>();
        Set<OptionRecord> optionRecords = new LinkedHashSet<>();
        for (Question dbQuestion : dbQuestions) {
            Optional<Map<String, Object>> first = questions.stream().filter(o -> Objects.equals(dbQuestion.getId(), o.get("qtId"))).findFirst();
            if (first.isPresent()) {
                if (dbQuestion.getType() == 0 || dbQuestion.getType() == 2) {
                    String radio = Objects.toString(first.get().get("radio"), null);
                    if (dbQuestion.getRequired() == 1 && StringUtils.isEmpty(radio)) {
                        return ApiResponse.error("问题[" + dbQuestion.getTitle() + "]必答！");
                    }
                    OptionRecord optionRecord = OptionRecord.builder().id(IDUtils.id())
                            .qnRcId(questionnaireRecord.getId())
                            .opId(radio).build();
                    optionRecords.add(optionRecord);
                }
                if (dbQuestion.getType() == 1) {
                    List<String> checked = (List<String>) first.get().get("checked");
                    if (dbQuestion.getRequired() == 1 && CollectionUtils.isEmpty(checked)) {
                        return ApiResponse.error("问题[" + dbQuestion.getTitle() + "]必答！");
                    }
                    OptionRecord optionRecord;
                    for (String check : checked) {
                        optionRecord = OptionRecord.builder().id(IDUtils.id())
                                .qnRcId(questionnaireRecord.getId())
                                .opId(check).build();
                        optionRecords.add(optionRecord);
                    }
                }
                if (dbQuestion.getType() == 3) {
                    String scoreString = Objects.toString(first.get().get("score"), null);
                    if (dbQuestion.getRequired() == 1 && StringUtils.isEmpty(scoreString)) {
                        return ApiResponse.error("问题[" + dbQuestion.getTitle() + "]必答！");
                    }
                    Integer score = Integer.parseInt(scoreString);
                    QuestionRecord questionRecord = QuestionRecord.builder().id(IDUtils.id())
                            .qnRcId(questionnaireRecord.getId())
                            .qtId(dbQuestion.getId())
                            .grade(score).build();
                    questionRecords.add(questionRecord);
                }
                if (dbQuestion.getType() == 4) {
                    String text = Objects.toString(first.get().get("text"), null);
                    if (dbQuestion.getRequired() == 1 && StringUtils.isEmpty(text)) {
                        return ApiResponse.error("问题[" + dbQuestion.getTitle() + "]必答！");
                    }
                    QuestionRecord questionRecord = QuestionRecord.builder().id(IDUtils.id())
                            .qnRcId(questionnaireRecord.getId())
                            .qtId(dbQuestion.getId())
                            .text(text).build();
                    questionRecords.add(questionRecord);
                }

            }
        }
        if (!CollectionUtils.isEmpty(questionRecords)) {
            SqlUtils.batchSave(QuestionRecordMapper.class, questionRecords);
        }
        if (!CollectionUtils.isEmpty(optionRecords)) {
            SqlUtils.batchSave(OptionRecordMapper.class, optionRecords);
        }
        questionnaireRecordMapper.insertSelective(questionnaireRecord);
        return ApiResponse.info("保存问卷成功");
    }

}

