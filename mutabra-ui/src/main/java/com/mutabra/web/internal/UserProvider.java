package com.mutabra.web.internal;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import org.greatage.security.AuthenticationException;
import org.greatage.security.PasswordAuthenticationProvider;
import org.greatage.security.PasswordEncoder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserProvider extends PasswordAuthenticationProvider<User> {
	private final BaseEntityService<Account, AccountQuery> accountService;

	public UserProvider(final PasswordEncoder passwordEncoder,
						final BaseEntityService<Account, AccountQuery> accountService) {
		super(passwordEncoder);
		this.accountService = accountService;
	}

	@Override
	protected User getAuthentication(final String name) {
		final Account account = accountService.query().withEmail(name).unique();
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
