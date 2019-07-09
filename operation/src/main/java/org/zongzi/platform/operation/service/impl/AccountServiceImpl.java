package org.zongzi.platform.operation.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.common.utils.CommonUtils;
import org.zongzi.platform.operation.domain.SAccount;
import org.zongzi.platform.operation.mapper.AccountMapper;
import org.zongzi.platform.operation.service.AccountService;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author haopeisong
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public ApiResponse<?> signup(SAccount account) {
        CommonUtils.setBeanInfo(account, false);
        account.setCreatorId(account.getId());
        accountMapper.insertSelective(account);
        return ApiResponse.info("注册成功！");
    }

    @Override
    public ApiResponse<?> save(Set<SAccount> accountSet) {
        if (CollectionUtils.isEmpty(accountSet)) {
            return ApiResponse.warning("没有可以新增的用户信息！");
        }
        for (SAccount SAccount : accountSet) {
            CommonUtils.setBeanInfo(SAccount, false);
            accountMapper.insertSelective(SAccount);
        }
        return ApiResponse.info("新增用户信息成功！");
    }

    @Override
    public ApiResponse<?> lock(Set<String> accountIdSet) {
        if (!CollectionUtils.isEmpty(accountIdSet)) {
            accountMapper.updateStatus(2, accountIdSet);
        }
        return ApiResponse.info("锁定用户成功！");
    }

    @Override
    public ApiResponse<?> unlock(Set<String> accountIdSet) {
        if (!CollectionUtils.isEmpty(accountIdSet)) {
            accountMapper.updateStatus(1, accountIdSet);
        }
        return ApiResponse.info("解锁用户成功！");
    }
}
