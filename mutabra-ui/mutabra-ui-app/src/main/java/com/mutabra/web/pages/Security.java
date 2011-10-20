package com.mutabra.web.pages;

import com.mutabra.domain.security.Account;
import com.mutabra.security.TwitterService;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.services.LinkManager;
import com.mutabra.web.services.MailService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecretEncoder;
import org.greatage.security.SecurityContext;
import org.greatage.util.DescriptionBuilder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Security extends AbstractPage {

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
	private SecretEncoder passwordEncoder;

	@Inject
	private LinkManager linkManager;

	@Inject
	private TwitterService twitterService;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		securityContext.signIn(new Credentials(email, password));
		return Index.class;
	}

	@OnEvent("signIn")
	Object signInOnce(final String email, final String token) {
		securityContext.signIn(new Credentials("token", email, token));
		return Index.class;
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
		account.setPassword(passwordEncoder.encode(generatedPassword));
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

	@OnEvent("facebookConnect")
	Object connectFacebook(
			@RequestParameter(value = "code", allowBlank = true) final String code,
			@RequestParameter(value = "error", allowBlank = true) final String error,
			@RequestParameter(value = "error_reason", allowBlank = true) final String errorReason,
			@RequestParameter(value = "error_description", allowBlank = true) final String errorDescription) {

		final String info = new DescriptionBuilder("FACEBOOK TOKEN")
				.append("code", code)
				.append("error", error)
				.append("error_reason", errorReason)
				.append("error_description", errorDescription)
				.toString();
		System.out.println(info);
		return null;
	}

	@OnEvent("twitterConnect")
	Object connectTwitter(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "denied", allowBlank = true) String denied) {

		final String info = new DescriptionBuilder("TWITTER TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.append("denied", denied)
				.toString();
		System.out.println(info);
		return null;
	}

	@OnEvent("googleConnect")
	Object connectGoogle(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier) {

		final String info = new DescriptionBuilder("GOOGLE TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.toString();
		System.out.println(info);
		return null;
	}

	@OnEvent("vKontakteConnect")
	Object connectVKontakte(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "denied", allowBlank = true) String denied) {

		final String info = new DescriptionBuilder("VKONTAKTE TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.append("denied", denied)
				.toString();
		System.out.println(info);
		return null;
	}

	private String generateRandomString() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
