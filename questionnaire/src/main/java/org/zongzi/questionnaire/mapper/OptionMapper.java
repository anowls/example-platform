package org.zongzi.questionnaire.mapper;

import org.apache.ibatis.annotations.Param;
import org.zongzi.platform.common.BaseMapper;
import org.zongzi.questionnaire.domain.Option;

import java.util.List;
import java.util.Set;

/**
 * @author haopeisong
 */
public interface OptionMapper extends BaseMapper<Option> {

    /**
     * 根据问题ID查询选项列表
     *
     * @param qtId 问题ID
     * @return 选项列表
     */
    List<Option> selectByQtId(String qtId);

    /**
     * 根据问题ID删除选项
     *
     * @param qtIdSet 问题ID集合
     * @return 成功删除条数
     */
    int deleteByQtId(@Param("qtIdSet") Set<String> qtIdSet);
}