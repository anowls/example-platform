package org.zongzi.questionnaire.mapper;

import org.zongzi.platform.common.BaseMapper;
import org.zongzi.questionnaire.domain.Questionnaire;

import java.util.List;
import java.util.Map;

/**
 * @author haopeisong
 */
public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {

    /**
     * 查询问卷列表
     *
     * @param filterMap 过滤条件
     * @return 问卷列表
     */
    List<Questionnaire> findAll(Map<String, Object> filterMap);
}