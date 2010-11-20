package com.noname.web.services.security;

import com.noname.domain.security.Account;
import com.noname.services.MailService;
import com.noname.services.security.AccountService;
import com.noname.web.pages.security.Activate;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.greatage.security.AuthenticationManager;
import org.greatage.security.DefaultAuthenticationToken;
import org.greatage.security.PasswordEncoder;
import org.greatage.security.UserContext;
import org.greatage.util.StringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityService {
	private final AccountService accountService;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final UserContext<GameUser> userContext;
	private final AuthenticationManager authenticationManager;
	private final PageRenderLinkSource linkSource;

	public SecurityService(final AccountService accountService,
						   final MailService mailService,
						   final PasswordEncoder passwordEncoder,
						   final UserContext<GameUser> userContext,
						   final AuthenticationManager authenticationManager,
						   final PageRenderLinkSource linkSource) {

		this.accountService = accountService;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
		this.userContext = userContext;
		this.authenticationManager = authenticationManager;
		this.linkSource = linkSource;
	}

	public Account getAccount() {
		return userContext.getUser().getAccount();
	}

	public boolean isAuthenticated() {
		return userContext.getUser() != null;
	}

	public void activate(final String email, final String token) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(token)) {
			throw new SecurityException("Wrong activation parameters");
		} else {
			final Account account = accountService.getAccount(email);
			if (account == null) {
				throw new SecurityException("Wrong activation parameters");
			} else if (!token.equals(account.getToken())) {
				throw new SecurityException("Wrong activation parameters");
			} else {
				accountService.activateAccount(account);
				userContext.setUser(new GameUser(account));
			}
		}
	}

	public void signUp(final String email) {
		if (accountService.getAccount(email) != null) {
			throw new SecurityException("Account already exists");
		} else {
			final String password = generateRandomString();
			final String token = generateRandomString();

			final Link link = linkSource.createPageRenderLinkWithContext(Activate.class, email, token);
			final String message = String.format("Hello Mr., New account was created for you, (login: %s, password: %s, activation token: %s). To activate your account please follow the link: %s", email, password, token, link.toAbsoluteURI());

			mailService.send(email, "New game account", message);
			accountService.createAccount(email, passwordEncoder.encode(password), token);
		}
	}

	public void signIn(final String email, final String password) {
		final DefaultAuthenticationToken token = new DefaultAuthenticationToken(email, password);
		final GameUser user = (GameUser) authenticationManager.authenticate(token);
		accountService.updateLastLogin(user.getAccount());
		userContext.setUser(user);
	}

	public void signOut() {
		userContext.setUser(null);
	}

	private String generateRandomString() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
