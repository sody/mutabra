package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityQuery;

/**
 * @author Ivan Khalopik
 */
public class AccountQuery extends BaseEntityQuery<Account, AccountQuery> implements AccountFilter {
	private String userName;

	public AccountQuery() {
		super(Account.class);
	}

	public AccountQuery withUserName(final String userName) {
		this.userName = userName;
		return query();
	}

	@Override
	public String getUserName() {
		return userName;
	}
}
