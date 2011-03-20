package com.noname.services.security;

import com.noname.domain.security.Account;
import com.noname.game.User;
import org.greatage.security.AuthenticationException;
import org.greatage.security.PasswordAuthenticationProvider;
import org.greatage.security.PasswordEncoder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserProvider extends PasswordAuthenticationProvider<User> {
	private final AccountService accountService;

	public UserProvider(final PasswordEncoder passwordEncoder, final AccountService accountService) {
		super(passwordEncoder);
		this.accountService = accountService;
	}

	@Override
	protected User getAuthentication(final String name) {
		final Account account = accountService.getAccount(name);
		if (account != null) {
			final User user = new User(account);
			if (user.getAuthorities().contains(AuthorityConstants.ROLE_PENDING)) {
				throw new AuthenticationException("Account must be confirmed");
			}
			return user;
		}
		return null;
	}
}
