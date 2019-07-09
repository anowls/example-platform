package org.zongzi.platform.common;

import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;

public interface BaseMapper<TT> extends tk.mybatis.mapper.common.BaseMapper<TT>, ExampleMapper<TT>, IdsMapper<TT> {
}
