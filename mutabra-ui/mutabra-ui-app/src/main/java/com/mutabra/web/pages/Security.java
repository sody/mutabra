package com.mutabra.web.pages;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.services.LinkManager;
import com.mutabra.web.services.MailService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecretEncoder;
import org.greatage.security.SecurityContext;
import org.greatage.util.DescriptionBuilder;

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

	@InjectComponent
	private Form signIn;

	@InjectComponent
	private Form signUp;

	@InjectComponent
	private Form restore;

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
	Object signUp() {
		//todo: move to validate method
		if (accountService.query().withEmail(email).unique() != null) {
			signUp.recordError("Account already exists");
			return null;
		}

		final String generatedToken = Authorities.generateSecret();
		final String generatedPassword = Authorities.generateSecret();

		final Account account = accountService.create();
		account.setEmail(email);
		account.setPassword(passwordEncoder.encode(generatedPassword));
		account.setToken(generatedToken);
		account.setRegistered(new Date());

		final Link link = linkManager.createPageEventLink(Security.class, "signIn", email, generatedToken);
		mailService.notifySignUp(email, generatedToken, link.toAbsoluteURI());
		accountService.save(account);

		return Index.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "restore")
	Object restorePassword() {
		//todo: move to validate method
		final Account account = accountService.query().withEmail(email).unique();
		if (account == null) {
			restore.recordError("Account doesn't exist.");
			return null;
		}
		if (account.getToken() != null) {
			restore.recordError("Can restore password only once.");
			return null;
		}

		final String generatedToken = Authorities.generateSecret();
		account.setToken(generatedToken);

		final Link link = linkManager.createPageEventLink(Security.class, "signIn", email, generatedToken);
		mailService.notifyRestorePassword(email, generatedToken, link.toAbsoluteURI());
		accountService.save(account);

		return Index.class;
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
}
