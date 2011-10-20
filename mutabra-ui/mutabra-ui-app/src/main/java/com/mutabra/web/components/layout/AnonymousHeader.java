package com.mutabra.web.components.layout;

import com.mutabra.domain.security.Account;
import com.mutabra.security.*;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.Index;
import com.mutabra.web.pages.Security;
import com.mutabra.web.services.LinkManager;
import com.mutabra.web.services.MailService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecretEncoder;
import org.greatage.security.SecurityContext;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnonymousHeader extends AbstractComponent {

	@Property
	private String email;

	@Property
	private String password;

	@Inject
	private SecurityContext securityContext;

	@InjectService("accountService")
	private BaseEntityService<Account, AccountQuery> accountService;

	@Inject
	private MailService mailService;

	@Inject
	private SecretEncoder secretEncoder;

	@Inject
	private LinkManager linkManager;

	@Inject
	private TwitterService twitterService;

	@Inject
	private FacebookService facebookService;

	@Inject
	private GoogleService googleService;

	@Inject
	private VKontakteService vkontakteService;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		securityContext.signIn(new Credentials(email, password));
		return getResources().getPageName();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signUp")
	Object signUp() throws ValidationException {
		if (accountService.query().withEmail(email).unique() != null) {
			throw new ValidationException("Account already exists");
		}

		final String generatedToken = generateRandomString();
		final String generatedPassword = generateRandomString();

		final Account account = accountService.create();
		account.setEmail(email);
		account.setPassword(secretEncoder.encode(generatedPassword));
		account.setToken(generatedToken);
		account.setRegistered(new Date());

		final Link link = linkManager.createPageEventLink(Security.class, "signIn", email, generatedToken);
		final String message = String.format("Hello Mr.,\n" +
				"New account was created for you,\n" +
				"(login: %s, password: %s, activation token: %s).\n" +
				"To activate your account please follow the link:\n %s",
				email, generatedPassword, generatedToken, link.toAbsoluteURI());
		mailService.send(email, "Mutabra Account", message);
		accountService.save(account);

		System.out.println("MESSAGE: " + message);
		return Index.class;
	}

	URL onConnectToFacebook() throws MalformedURLException {
		return createAuthenticationURL(facebookService);
	}

	URL onConnectToGoogle() throws MalformedURLException {
		return createAuthenticationURL(googleService);
	}

	URL onConnectToTwitter() throws MalformedURLException {
		return createAuthenticationURL(twitterService);
	}

	URL onConnectToVKontakte() throws MalformedURLException {
		return createAuthenticationURL(vkontakteService);
	}

	private URL createAuthenticationURL(final BaseOAuthService authService) throws MalformedURLException {
		return new URL(authService.getAuthorizationURL());
	}

	private String generateRandomString() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
