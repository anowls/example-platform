package org.zongzi.questionnaire.service;

import org.zongzi.platform.common.ApiResponse;
import org.zongzi.questionnaire.domain.Question;
import org.zongzi.questionnaire.domain.Questionnaire;

import java.util.List;
import java.util.Map;

/**
 * @author haopeisong
 */
public interface QuestionnaireService {

    /**
     * 查询问卷列表
     *
     * @return 问卷列表
     */
    ApiResponse selectAll(Map<String, String> filter);

    /**
     * 查询问卷详情
     *
     * @param id 问卷ID
     * @return 问卷详情
     */
    ApiResponse selectById(String id);

    /**
     * 新增、修改问卷基础信息，撤销问卷删除答案
     *
     * @param questionnaire 问卷对象
     * @return 操作结果
     */
    ApiResponse insert(Questionnaire questionnaire);

    /**
     * 刪除问卷
     *
     * @param id 问卷ID
     * @return 操作结果
     */
    ApiResponse deleteByPrimaryKey(String id);

    /**
     * 保存试卷
     * 先删除相关，在保存所有
     *
     * @param id 问卷ID
     * @return 操作结果
     */
    ApiResponse upper(String id);

    /**
     * 保存试卷
     * 先删除相关，在保存所有
     *
     * @param id 问卷ID
     * @return 操作结果
     */
    ApiResponse stop(String id);

    /**
     * 保存试卷
     * 先删除相关，在保存所有
     *
     * @param id 问卷ID
     * @return 操作结果
     */
    ApiResponse back(String id);

    /**
     * 保存试卷
     * 先删除相关，在保存所有
     *
     * @param questions 问卷对象
     * @return 操作结果
     */
    ApiResponse save(String id, List<Question> questions);

}
