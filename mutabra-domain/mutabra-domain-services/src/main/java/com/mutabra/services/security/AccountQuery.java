package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountQuery extends BaseEntityQuery<Account, AccountQuery> {
	private String userName;

	public AccountQuery(final EntityRepository repository) {
		super(repository, Account.class);
	}

	public AccountQuery withEmail(final String userName) {
		this.userName = userName;
		return query();
	}

	public String getEmail() {
		return userName;
	}
}
