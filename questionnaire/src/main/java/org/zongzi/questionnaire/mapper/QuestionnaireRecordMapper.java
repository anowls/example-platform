package org.zongzi.questionnaire.mapper;

import org.zongzi.platform.common.BaseMapper;
import org.zongzi.questionnaire.domain.QuestionnaireRecord;

/**
 * @author haopeisong
 */
public interface QuestionnaireRecordMapper extends BaseMapper<QuestionnaireRecord> {

    /**
     * 根据问卷ID删除问卷记录
     *
     * @param qnId 问卷ID
     * @return 成功删除条数
     */
    int deleteByQnId(String qnId);

}