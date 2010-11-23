package com.noname.web.services;

import com.noname.domain.security.Account;
import com.noname.services.security.AccountService;
import org.greatage.security.Authentication;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationContextImpl implements ApplicationContext {
	private final SecurityContext securityContext;
	private final AccountService accountService;

	private Account account;

	public ApplicationContextImpl(final SecurityContext securityContext, final AccountService accountService) {
		this.securityContext = securityContext;
		this.accountService = accountService;
	}

	public Account getAccount() {
		if (account == null) {
			final Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				account = accountService.getAccount(authentication.getName());
			}
		}
		return account;
	}

}
