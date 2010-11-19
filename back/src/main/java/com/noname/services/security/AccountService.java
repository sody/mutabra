package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountService extends BaseEntityService<Account> {

	Account getAccount(String email);

	void createAccount(String email, String password, String token);

	void activateAccount(Account account);

	void updateLastLogin(Account account);

}
