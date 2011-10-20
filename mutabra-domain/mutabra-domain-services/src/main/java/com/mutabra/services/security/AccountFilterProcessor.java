package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityFilterProcessor;
import org.greatage.domain.EntityCriteria;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountFilterProcessor extends BaseEntityFilterProcessor<Account, AccountQuery> {

	public AccountFilterProcessor() {
		super(Account.class);
	}

	@Override
	protected void processFilter(final EntityCriteria criteria, final AccountQuery filter) {
		if (!StringUtils.isEmpty(filter.getEmail())) {
			criteria.add(criteria.getProperty(Account.EMAIL_PROPERTY).eq(filter.getEmail()));
		}

		if (!StringUtils.isEmpty(filter.getPassword())) {
			criteria.add(criteria.getProperty(Account.PASSWORD_PROPERTY).eq(filter.getPassword()));
		}

		if (!StringUtils.isEmpty(filter.getToken())) {
			criteria.add(criteria.getProperty(Account.TOKEN_PROPERTY).eq(filter.getToken()));
		}
	}
}
