package org.zongzi.platform.operation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.zongzi.platform.operation.service.AccountService;

/**
 * @author haopeisong
 *
 */
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	
}
