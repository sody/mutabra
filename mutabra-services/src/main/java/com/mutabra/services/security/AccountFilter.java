package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountFilter extends BaseEntityFilter<Account> {

	String getEmail();

}
