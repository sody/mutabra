package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityServiceImpl;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 */
public class AccountServiceImpl
		extends BaseEntityServiceImpl<Account, AccountQuery>
		implements AccountService {

	public AccountServiceImpl(final EntityRepository repository) {
		super(repository, Account.class, AccountQuery.class);
	}

	@Override
	public Account getAccount(String username) {
		return createQuery().withUserName(username).unique();
	}
}
