package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountFilter extends BaseEntityFilter<Account> {

	String getEmail();

}
