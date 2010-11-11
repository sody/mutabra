package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityServiceImpl;
import com.noname.services.MailService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 */
public class AccountServiceImpl
		extends BaseEntityServiceImpl<Account, AccountQuery>
		implements AccountService {

	private final MailService mailService;

	public AccountServiceImpl(final EntityRepository repository, final MailService mailService) {
		super(repository, Account.class, AccountQuery.class);
		this.mailService = mailService;
	}

	@Override
	public Account getAccount(String username) {
		return createQuery().withUserName(username).unique();
	}

	@Override
	public void createAccount(String email) {
		mailService.send(email, "New Account", "Hello, World!");
	}
}
