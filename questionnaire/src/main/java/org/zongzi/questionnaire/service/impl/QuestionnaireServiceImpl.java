package org.zongzi.questionnaire.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.common.utils.CommonUtils;
import org.zongzi.platform.common.utils.ExampleUtils;
import org.zongzi.questionnaire.common.ParameterMapUtils;
import org.zongzi.questionnaire.domain.Option;
import org.zongzi.questionnaire.domain.OptionRecord;
import org.zongzi.questionnaire.domain.Question;
import org.zongzi.questionnaire.domain.QuestionRecord;
import org.zongzi.questionnaire.domain.Questionnaire;
import org.zongzi.questionnaire.domain.QuestionnaireRecord;
import org.zongzi.questionnaire.mapper.OptionMapper;
import org.zongzi.questionnaire.mapper.OptionRecordMapper;
import org.zongzi.questionnaire.mapper.QuestionMapper;
import org.zongzi.questionnaire.mapper.QuestionRecordMapper;
import org.zongzi.questionnaire.mapper.QuestionnaireMapper;
import org.zongzi.questionnaire.mapper.QuestionnaireRecordMapper;
import org.zongzi.questionnaire.service.QuestionnaireService;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author haopiesong
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Autowired
    private QuestionnaireRecordMapper questionnaireRecordMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionRecordMapper questionRecordMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private OptionRecordMapper optionRecordMapper;

    @Override
    public ApiResponse selectAll(Map<String, String> filter) {
        Map<String, Object> filterMap = ParameterMapUtils.convertParameter(filter);
        List<Questionnaire> questionnaires = questionnaireMapper.findAll(filterMap);
        if (questionnaires instanceof Page) {
            PageInfo<Questionnaire> questionnairePageInfo = new PageInfo<>(questionnaires);
            return ApiResponse.success(questionnairePageInfo);
        }
        return ApiResponse.success(questionnaires);
    }

    @Override
    public ApiResponse selectById(String id) {
        Questionnaire questionnaire = questionnaireMapper.selectByPrimaryKey(id);
        if (questionnaire != null) {
            List<Question> questions = questionMapper.selectByQnId(id);
            if (!CollectionUtils.isEmpty(questions)) {
                for (Question question : questions) {
                    List<Option> options = optionMapper.selectByQtId(question.getId());
                    question.setExtraMap("options", CollectionUtils.isEmpty(options) ? Collections.emptyList() : options);
                }
            }
            questionnaire.setExtraMap("questions", questions);
            return ApiResponse.success(questionnaire);
        }
        return ApiResponse.info("该问卷可能已被删除！");
    }

    /**
     * 新增、修改问卷基础信息，撤销问卷删除答案
     *
     * @param questionnaire 问卷对象
     * @return 操作结果
     */
    @Override
    public ApiResponse insert(Questionnaire questionnaire) {
        String id;
        Questionnaire dbQuestionnaire = null;
        if (StringUtils.hasLength((id = questionnaire.getId()))) {
            dbQuestionnaire = questionnaireMapper.selectByPrimaryKey(id);
        }
        Short status;
        status = (status = questionnaire.getStatus()) == null ? Short.valueOf("0") : status;
        // 修改
        if (dbQuestionnaire != null) {
            if (dbQuestionnaire.getStatus() != 0) {
                return ApiResponse.error("已发布、已结束问卷不能修改");
            }
            CommonUtils.setBeanInfo(questionnaire, true);
            questionnaire.setStatus(status);
            questionnaire.setDeleted(false);
            questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
            return ApiResponse.success("修改成功！");
        }
        CommonUtils.setBeanInfo(questionnaire, false);
        questionnaire.setStatus(status);
        questionnaire.setDeleted(false);
        questionnaireMapper.insertSelective(questionnaire);
        return ApiResponse.success("新增成功！");
    }

    /**
     * 刪除问卷
     *
     * @param id 问卷id
     * @return 操作结果
     */
    @Override
    public ApiResponse deleteByPrimaryKey(String id) {
        Questionnaire questionnaire = questionnaireMapper.selectByPrimaryKey(id);
        if (questionnaire == null) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        if (questionnaire.getStatus() != 0) {
            return ApiResponse.error("已发布、已结束问卷不能删除！");
        }
        List<Question> questions = questionMapper.selectByQnId(id);
        if (!CollectionUtils.isEmpty(questions)) {
            questionMapper.deleteByQnId(id);
            Set<String> qtIdSet = questions.stream().map(Question::getId).collect(Collectors.toSet());

            // 删除问题选项和选项答案
            Weekend<Option> example = ExampleUtils.example(Option.class, o -> o.andIn(Option::getQtId, qtIdSet));
            List<Option> options = optionMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(options)) {
                optionMapper.deleteByQtId(qtIdSet);

                Set<String> otIdSet = options.stream().map(Option::getId).collect(Collectors.toSet());
                optionRecordMapper.deleteByOtId(otIdSet);
            }

            // 删除评分题和简单题答案
            questionRecordMapper.deleteByQtId(qtIdSet);
        }
        questionnaireRecordMapper.deleteByQnId(id);
        questionnaireMapper.deleteByPrimaryKey(id);
        return ApiResponse.success("刪除成功");
    }

    @Override
    public ApiResponse upper(String id) {
        Questionnaire questionnaire = questionnaireMapper.selectByPrimaryKey(id);
        if (null == questionnaire) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        if (questionnaire.getStatus() == 0) {
            questionnaire.setStatus(Short.valueOf("1"));
            questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
            return ApiResponse.success("发布成功！");
        }
        return ApiResponse.error("已发布、已结束问卷不能再次发布！");
    }

    @Override
    public ApiResponse stop(String id) {
        Questionnaire questionnaire = questionnaireMapper.selectByPrimaryKey(id);
        if (null == questionnaire) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        if (questionnaire.getStatus() == 1) {
            questionnaire.setStatus(Short.valueOf("2"));
            questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
            return ApiResponse.success("结束成功！");
        }
        return ApiResponse.error("未发布、已结束问卷不能再次结束！");
    }

    @Override
    public ApiResponse back(String id) {
        Questionnaire questionnaire = questionnaireMapper.selectByPrimaryKey(id);
        if (null == questionnaire) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        if (questionnaire.getStatus() == 1) {
            Weekend<QuestionnaireRecord> example = ExampleUtils.example(QuestionnaireRecord.class,
                    o -> o.andEqualTo(QuestionnaireRecord::getQnId, id));
            List<QuestionnaireRecord> questionnaireRecords = questionnaireRecordMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(questionnaireRecords)) {
                Set<String> qnrcIds = questionnaireRecords.stream().map(QuestionnaireRecord::getId).collect(Collectors.toSet());
                Weekend<QuestionRecord> qtrcExample = ExampleUtils.example(QuestionRecord.class,
                        o -> o.andIn(QuestionRecord::getQnRcId, qnrcIds));
                questionRecordMapper.deleteByExample(qtrcExample);

                Weekend<OptionRecord> qorcExample = ExampleUtils.example(OptionRecord.class,
                        o -> o.andIn(OptionRecord::getQnRcId, qnrcIds));
                optionRecordMapper.deleteByExample(qorcExample);
            }
            questionnaire.setStatus(Short.valueOf("0"));
            questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
            return ApiResponse.success("撤销成功！");
        }
        return ApiResponse.error("未发布、已结束问卷不能撤销！");
    }

    @Override
    public ApiResponse save(String id, List<Question> questions) {
        Questionnaire questionnaire = questionnaireMapper.selectByPrimaryKey(id);
        if (questionnaire == null) {
            return ApiResponse.error("该问卷可能已被删除！");
        }
        ApiResponse apiResponse = deleteByPrimaryKey(id);
        if (Objects.equals(apiResponse.getLevel(), "success")) {

            return ApiResponse.info("保存成功");
        }
        return ApiResponse.error("保存失败");
    }
}

