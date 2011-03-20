package com.noname.services.security;

import com.mutabra.domain.security.Account;
import com.noname.services.BaseEntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountFilter extends BaseEntityFilter<Account> {

	String getEmail();

}
