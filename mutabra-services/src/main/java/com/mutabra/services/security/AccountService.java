package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountService extends BaseEntityService<Account> {

	Account getAccount(String email);

	void updateLastLogin(String email);

	void createAccount(String email, String password, String token);

	void activateAccount(Account account);

}
