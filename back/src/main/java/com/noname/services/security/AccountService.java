package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 */
public interface AccountService extends BaseEntityService<Account> {

	Account getAccount(String username);

}
