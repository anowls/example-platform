package org.zongzi.questionnaire.service;

import org.zongzi.platform.common.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * @author haopeisong
 */
public interface QuestionAnswerService {

    /**
     * 获取当前用户参与的所有问卷（）
     *
     * @return 所有问卷
     */
    ApiResponse selectAll(Map<String, String> filter);

    /**
     * 按照Id查询试卷答案
     *
     * @param id     问卷id
     * @param userId 用户id
     * @return 试卷答案
     */
    ApiResponse selectAnsById(String id, String userId);

    /**
     * @param id         问卷ID
     * @param operatorId 操作人ID
     * @param questions  问题答案
     *                   <pre>
     *                   { qtId: 问题ID, radio: 单选题答案，选项ID, checked: 多选题答案，选项ID集合, score: 评分题答案，分数, text: 解答题答案}
     *                   </pre>
     * @return 操作结果
     */
    ApiResponse save(String id, String operatorId, List<Map<String, Object>> questions);
}
