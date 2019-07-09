package org.zongzi.platform.operation.service;

import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.operation.domain.SAccount;

import java.util.Set;

/**
 * @author haopeisong
 */
public interface AccountService {

    /**
     * 注册用户账户
     *
     * @param SAccount 用户账户信息
     * @return 操作结果
     */
    ApiResponse<?> signup(SAccount SAccount);

    /**
     * 批量保存用户账户信息
     *
     * @param SAccountSet 用户账户信息集合
     * @return 操作结果
     */
    ApiResponse<?> save(Set<SAccount> SAccountSet);

    /**
     * 锁定用户
     *
     * @param accountIdSet 用户ID集合
     * @return 操作结果
     */
    ApiResponse<?> lock(Set<String> accountIdSet);

    /**
     * 解锁用户
     *
     * @param accountIdSet 用户ID集合
     * @return 操作结果
     */
    ApiResponse<?> unlock(Set<String> accountIdSet);
}
