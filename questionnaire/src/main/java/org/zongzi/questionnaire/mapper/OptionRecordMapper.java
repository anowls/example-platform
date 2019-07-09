package org.zongzi.questionnaire.mapper;

import org.apache.ibatis.annotations.Param;
import org.zongzi.platform.common.BaseMapper;
import org.zongzi.questionnaire.domain.OptionRecord;

import java.util.Set;

/**
 * @author haoipeisong
 */
public interface OptionRecordMapper extends BaseMapper<OptionRecord> {

    /**
     * 根据选项ID集合删除选项记录
     *
     * @param otIds 选项ID集合
     * @return 成功删除条数
     */
    int deleteByOtId(@Param("otIds") Set<String> otIds);

    /**
     * 根据问卷记录ID集合删除选项记录
     *
     * @param qnRcIds 问卷记录ID集合
     * @return 成功删除条数
     */
    int deleteByQnRcId(@Param("qnRcIds") Set<String> qnRcIds);

}