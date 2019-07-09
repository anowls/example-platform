package org.zongzi.platform.operation.mapper;

import org.zongzi.platform.common.BaseMapper;
import org.zongzi.platform.operation.domain.SAccount;

import java.util.Set;

/**
 * @author haopeisong
 */
public interface AccountMapper extends BaseMapper<SAccount> {

    /**
     * 根据用户登录名查询用户信息
     *
     * @param username 用户登录名
     * @return 用户信息
     */
    SAccount queryByUsername(String username);

    /**
     * 更新用户的状态值
     *
     * @param statusValue  状态值{@link SAccount#getStatus()}
     * @param accountIdSet 用户ID集合
     * @return 更新条数
     */
    int updateStatus(int statusValue, Set<String> accountIdSet);
}
