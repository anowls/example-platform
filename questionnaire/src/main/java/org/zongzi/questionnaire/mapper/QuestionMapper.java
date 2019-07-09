package org.zongzi.questionnaire.mapper;

import org.zongzi.platform.common.BaseMapper;
import org.zongzi.questionnaire.domain.Question;

import java.util.List;

/**
 * @author haopeisong
 */
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 根据问卷ID查询问题列表
     *
     * @param qnId 问卷ID
     * @return 问题列表
     */
    List<Question> selectByQnId(String qnId);

    /**
     * 根据问卷ID删除问题
     *
     * @param qnId 问卷ID
     * @return 成功删除条数
     */
    int deleteByQnId(String qnId);
}