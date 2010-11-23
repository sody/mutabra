package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.domain.security.Role;
import com.noname.services.BaseEntityServiceImpl;
import org.greatage.domain.EntityRepository;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountServiceImpl
		extends BaseEntityServiceImpl<Account, AccountQuery>
		implements AccountService {

	private final RoleService roleService;

	public AccountServiceImpl(final EntityRepository repository, final RoleService roleService) {
		super(repository, Account.class, AccountQuery.class);
		this.roleService = roleService;
	}

	public Account getAccount(final String email) {
		return createQuery().withEmail(email).unique();
	}

	public void updateLastLogin(final String email) {
		final Account account = getAccount(email);
		if (account != null) {
			account.setLastLogin(new Date());
			update(account);
		}
	}

	public void createAccount(final String email, final String password, final String token) {
		final Account account = create();
		account.setEmail(email);
		account.setPassword(password);
		account.setToken(token);
		account.setRegistered(new Date());
		final Role role = roleService.getPendingRole();
		account.getRoles().add(role);
		save(account);
	}

	public void activateAccount(final Account account) {
		account.setToken(null);
		account.setLastLogin(new Date());
		final Role role = roleService.getUserRole();
		account.getRoles().clear();
		account.getRoles().add(role);
		update(account);
	}
}
