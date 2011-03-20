package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityFilterProcessor;
import org.greatage.domain.EntityCriteria;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountFilterProcessor extends BaseEntityFilterProcessor<Account, AccountFilter> {

	public AccountFilterProcessor() {
		super(Account.class);
	}

	@Override
	protected void processFilter(final EntityCriteria criteria, final AccountFilter filter) {
		if (!StringUtils.isEmpty(filter.getEmail())) {
			criteria.add(criteria.getProperty(Account.EMAIL_PROPERTY).eq(filter.getEmail()));
		}
	}
}
