package com.mutabra.web.pages;

import com.mutabra.domain.game.Account;
import com.mutabra.security.OAuth;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.security.FacebookRealm;
import com.mutabra.web.internal.security.GoogleRealm;
import com.mutabra.web.internal.security.TwitterRealm;
import com.mutabra.web.internal.security.VKRealm;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.MailService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Security extends AbstractPage {
	private static final String APPLY_EVENT = "apply";

	private static final RandomNumberGenerator GENERATOR = new SecureRandomNumberGenerator();

	@Property
	private String email;

	@Property
	private String password;

	@InjectComponent
	private Form signInForm;

	@InjectComponent
	private Form restoreForm;

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

	@Inject
	private HashService hashService;

	@Inject
	private MailService mailService;

	public Link createApplyChangesLink(final String email, final String token) {
		return getResources().createEventLink(APPLY_EVENT, email, token);
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signInForm")
	Object signIn() {
		getSubject().login(new UsernamePasswordToken(email, password));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	Object facebookConnected(final OAuth.Session session) {
		getSubject().login(new FacebookRealm.Token(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	Object twitterConnected(final OAuth.Session session) {
		getSubject().login(new TwitterRealm.Token(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "google")
	Object googleConnected(final OAuth.Session session) {
		getSubject().login(new GoogleRealm.Token(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "vk")
	Object vkConnected(final OAuth.Session session) {
		getSubject().login(new VKRealm.Token(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "restoreForm")
	Object restorePassword() {
		final Account account = accountService.query()
				.filter(account$.email$.eq(email))
				.unique();
		// validate
		if (account == null || account.getToken() != null) {
			restoreForm.recordError(message("error.restore-error"));
			return null;
		}

		final String token = generateSecret();
		final String password = generateSecret();
		final Hash hash = generateHash(password);

		account.setToken(token);
		account.setPendingPassword(hash.toBase64());
		account.setPendingSalt(hash.getSalt().toBase64());

		final Link link = createApplyChangesLink(email, token);
		mailService.send(
				email,
				message("mail.restore-password.title"),
				format("mail.restore-password.body", email, password, link.toAbsoluteURI()));
		accountService.saveOrUpdate(account);
		return Index.class;
	}

	@OnEvent(APPLY_EVENT)
	Object applyPendingChanges(final String email, final String token) {
//		securityContext.signIn(new Credentials("token", email, token));
		return GameHome.class;
	}

	private Hash generateHash(final String secret) {
		return hashService.computeHash(new HashRequest.Builder().setSource(secret).build());
	}

	private String generateSecret() {
		return GENERATOR.nextBytes().toBase64();
	}
}
