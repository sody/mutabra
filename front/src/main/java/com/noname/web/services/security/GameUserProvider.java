package com.noname.web.services.security;

import com.noname.domain.security.Account;
import com.noname.services.security.AccountService;
import com.noname.web.services.AuthorityConstants;
import org.greatage.security.AuthenticationException;
import org.greatage.security.DefaultAuthenticationProvider;
import org.greatage.security.PasswordEncoder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GameUserProvider extends DefaultAuthenticationProvider {
	private final AccountService accountService;

	public GameUserProvider(final PasswordEncoder passwordEncoder, final AccountService accountService) {
		super(passwordEncoder);
		this.accountService = accountService;
	}

	@Override
	protected GameUser getAuthentication(final String name) {
		final Account account = accountService.getAccount(name);
		if (account != null) {
			final GameUser user = new GameUser(account);
			if (user.getAuthorities().contains(AuthorityConstants.ROLE_PENDING)) {
				throw new AuthenticationException("Account must be confirmed");
			}
			return user;
		}
		return null;
	}
}
