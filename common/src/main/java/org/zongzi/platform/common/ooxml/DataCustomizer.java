package org.zongzi.platform.common.ooxml;

import java.util.List;

public interface DataCustomizer<S> {

    /**
     * 获取表格头所在行的索引，根据实际情况自己定义
     *
     * @return 表格头所在行索引
     */
    int getHeaderIndex();

    /**
     * 验证解析出来的数据
     *
     * @param parsedRowList 解析出来的数据
     * @return 验证结果，true 验证通过，false 验证不通过
     */
    boolean verify(List<ParsedRow<S>> parsedRowList);
}
