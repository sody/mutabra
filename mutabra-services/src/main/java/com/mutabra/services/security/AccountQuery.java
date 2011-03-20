package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountQuery extends BaseEntityQuery<Account, AccountQuery> implements AccountFilter {
	private String userName;

	public AccountQuery() {
		super(Account.class);
	}

	public AccountQuery withEmail(final String userName) {
		this.userName = userName;
		return query();
	}

	@Override
	public String getEmail() {
		return userName;
	}
}
