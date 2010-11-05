package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityFilter;

/**
 * @author Ivan Khalopik
 */
public interface AccountFilter extends BaseEntityFilter<Account> {

	String getUserName();

}
