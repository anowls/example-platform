package org.zongzi.platform.operation.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zongzi.platform.operation.AppTest;
import org.zongzi.platform.common.ApiResponse;
import org.zongzi.platform.operation.domain.SAccount;

import java.util.Collections;

public class SAccountServiceTest extends AppTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void save() {
        SAccount SAccount = new SAccount("superadmin", "Abcd1234", "超级管理员", "", (byte) 1);
        ApiResponse<?> save = accountService.save(Collections.singleton(SAccount));
        Assert.assertEquals("success", save.getLevel());
    }
}
