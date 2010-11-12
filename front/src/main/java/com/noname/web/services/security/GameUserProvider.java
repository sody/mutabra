package com.noname.web.services.security;

import com.noname.domain.security.Account;
import com.noname.services.security.AccountService;
import org.greatage.security.auth.AuthenticationException;
import org.greatage.security.auth.DefaultAuthenticationProvider;
import org.greatage.security.auth.PasswordEncoder;
import org.greatage.util.StringUtils;

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
		if (account != null && !StringUtils.isEmpty(account.getToken())) {
			throw new AuthenticationException("Account must be confirmed");
		}
		return account != null ? new GameUser(account) : null;
	}
}
