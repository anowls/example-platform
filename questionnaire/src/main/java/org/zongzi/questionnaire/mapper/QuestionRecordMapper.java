package org.zongzi.questionnaire.mapper;

import org.apache.ibatis.annotations.Param;
import org.zongzi.platform.common.BaseMapper;
import org.zongzi.questionnaire.domain.QuestionRecord;

import java.util.Set;

/**
 * @author haopeisong
 */
public interface QuestionRecordMapper extends BaseMapper<QuestionRecord> {

    /**
     * 根据问题ID集合删除问题答案
     *
     * @param qtIds 问题ID集合
     * @return 成功删除条数
     */
    int deleteByQtId(@Param("qtIds") Set<String> qtIds);


    /**
     * 根据问卷记录ID集合删除选项记录
     *
     * @param qnRcIds 问卷记录ID集合
     * @return 成功删除条数
     */
    int deleteByQnRcId(@Param("qnRcIds") Set<String> qnRcIds);
}