package com.mutabra.web.pages;

import com.mutabra.domain.game.Account;
import com.mutabra.security.OAuth;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.security.ConfirmationRealm;
import com.mutabra.web.internal.security.FacebookRealm;
import com.mutabra.web.internal.security.GoogleRealm;
import com.mutabra.web.internal.security.TwitterRealm;
import com.mutabra.web.internal.security.VKRealm;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.MailService;
import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Hash;
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

	@Property
	private String email;

	@Property
	private String password;

	private Account account;

	@InjectComponent
	private Form signInForm;

	@InjectComponent
	private Form restoreForm;

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

	@Inject
	private PasswordGenerator generator;

	@Inject
	private MailService mailService;

	public Link createApplyChangesLink(final Account account, final String token) {
		return getResources().createEventLink(APPLY_EVENT, account, token);
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

	@OnEvent(value = EventConstants.VALIDATE, component = "restoreForm")
	void validateRestoreForm() {
		account = accountService.query()
				.filter(account$.email$.eq(email))
				.unique();
		if (account == null) {
			// user with specified email doesn't exist
			restoreForm.recordError(message("error.restore-password.unknown"));
		} else if (account.getToken() != null) {
			// user already has pending changes
			restoreForm.recordError(message("error.restore-password.try-again-later"));
		}
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "restoreForm")
	Object restorePassword() {
		// we should generate new password
		// and create auth token to confirm password changes
		// when user will confirm this from his email new password will be applied
		// and he will be automatically authenticated
		final String token = generator.generateSecret();
		final String password = generator.generateSecret();
		final Hash hash = generator.generateHash(password);

		account.setToken(token);
		account.setPendingPassword(hash.toBase64());
		account.setPendingSalt(hash.getSalt().toBase64());

		final Link link = createApplyChangesLink(account, token);
		mailService.send(
				account.getEmail(),
				message("mail.restore-password.title"),
				format("mail.restore-password.body", account.getEmail(), password, link.toAbsoluteURI()));
		accountService.saveOrUpdate(account);
		//todo: add mail sent notification
		return Index.class;
	}

	@OnEvent(APPLY_EVENT)
	Object applyPendingChanges(final Account account, final String token) {
		getSubject().login(new ConfirmationRealm.Token(account, token));
		//todo: add notification when pending changes was confirmed
		return GameHome.class;
	}
}
