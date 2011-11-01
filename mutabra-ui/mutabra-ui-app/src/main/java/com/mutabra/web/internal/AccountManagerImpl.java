package com.mutabra.web.internal;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.pages.Security;
import com.mutabra.web.services.AccountManager;
import com.mutabra.web.services.MailService;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.services.RequestPageCache;
import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.greatage.security.SecretEncoder;
import org.greatage.util.StringUtils;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountManagerImpl implements AccountManager {
	private final BaseEntityService<Account, AccountQuery> accountService;
	private final MailService mailService;
	private final SecretEncoder passwordEncoder;
	private final RequestPageCache requestPageCache;
	private final ComponentClassResolver componentClassResolver;

	public AccountManagerImpl(
			final @InjectService("accountService") BaseEntityService<Account, AccountQuery> accountService,
			final SecretEncoder passwordEncoder,
			final MailService mailService,
			final RequestPageCache requestPageCache,
			final ComponentClassResolver componentClassResolver) {

		this.accountService = accountService;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
		this.requestPageCache = requestPageCache;
		this.componentClassResolver = componentClassResolver;
	}

	public void signUp(final String email) throws ValidationException {
		if (accountService.query().withEmail(email).unique() != null) {
			//todo: add localization
			throw new ValidationException("Account already exists.");
		}

		final String generatedToken = Authorities.generateSecret();
		final String generatedPassword = Authorities.generateSecret();

		final Account account = accountService.create();
		account.setEmail(email);
		account.setPendingPassword(passwordEncoder.encode(generatedPassword));
		account.setToken(generatedToken);
		account.setRegistered(new Date());

		account.setPendingEmail(null);
		account.setPendingToken(null);

		final Link link = createTokenLink(email, generatedToken);
		final String message = String.format(SIGN_UP_NOTIFICATION, email, generatedToken, link.toAbsoluteURI(), DEFAULT_SIGNATURE);
		mailService.send(email, "mutabra: new account", message);
		accountService.save(account);
	}

	public void restorePassword(final String email) throws ValidationException {
		final Account account = accountService.query().withEmail(email).unique();
		if (account == null) {
			//todo: add localization
			throw new ValidationException("Account doesn't exist.");
		}
		if (account.getToken() != null) {
			//todo: add localization
			throw new ValidationException("Can restore password only once.");
		}

		final String generatedToken = Authorities.generateSecret();
		account.setToken(generatedToken);

		account.setPendingEmail(null);
		account.setPendingPassword(null);
		account.setPendingToken(null);

		final Link link = createTokenLink(email, generatedToken);
		final String message = String.format(RESTORE_PASSWORD_NOTIFICATION, email, generatedToken, link.toAbsoluteURI(), DEFAULT_SIGNATURE);
		mailService.send(email, "mutabra: password retrieval", message);
		accountService.update(account);
	}

	public void changePassword(final String email, final String password) {
		final Account account = accountService.query().withEmail(email).unique();

		final String generatedToken = Authorities.generateSecret();
		account.setToken(generatedToken);
		account.setPendingPassword(passwordEncoder.encode(password));

		account.setPendingEmail(null);
		account.setPendingToken(null);

		final Link link = createTokenLink(email, generatedToken);
		final String message = String.format(CHANGE_PASSWORD_NOTIFICATION, email, generatedToken, link.toAbsoluteURI(), DEFAULT_SIGNATURE);
		mailService.send(email, "mutabra: password change", message);
		accountService.update(account);
	}

	public void changeEmail(final String oldEmail, final String newEmail) {
		final Account tempAccount = accountService.create();
		tempAccount.setEmail(newEmail);

		final Account account = accountService.query().withEmail(oldEmail).unique();
		final String generatedToken = Authorities.generateSecret();
		account.setToken(generatedToken);
		final String generatedPendingToken = Authorities.generateSecret();
		account.setPendingToken(generatedPendingToken);
		account.setPendingEmail(newEmail);

		account.setPendingPassword(null);

		final Link link = createTokenLink(oldEmail, generatedToken);
		final String message = String.format(CHANGE_EMAIL_NOTIFICATION, oldEmail, newEmail, generatedToken, link.toAbsoluteURI(), DEFAULT_SIGNATURE);

		final Link link2 = createConfirmLink(oldEmail, generatedPendingToken);
		final String message2 = String.format(CHANGE_EMAIL_NOTIFICATION, oldEmail, newEmail, generatedPendingToken, link2.toAbsoluteURI(), DEFAULT_SIGNATURE);

		mailService.send(oldEmail, "mutabra: email change", message);
		mailService.send(newEmail, "mutabra: email change", message2);
		accountService.update(account);
		accountService.save(tempAccount);
	}

	public void confirmEmail(final String email, final String token) throws ValidationException {
		final Account account = accountService.query().withEmail(email).withPendingToken(token).unique();
		if (account == null) {
			//todo: add localization
			throw new ValidationException("Wrong credentials.");
		}
		if (account.getToken() == null && account.getPendingEmail() != null) {
			final Account tempAccount = accountService.query().withPendingEmail(account.getPendingEmail()).unique();
			accountService.delete(tempAccount);
			account.setEmail(account.getPendingEmail());
			account.setPendingEmail(null);
		}
		account.setPendingToken(null);
		accountService.update(account);
	}

	private Link createConfirmLink(final String oldEmail, final String generatedPendingToken) {
		return createPageEventLink(Security.class, "confirmEmail", oldEmail, generatedPendingToken);
	}

	private Link createTokenLink(final String email, final String generatedToken) {
		return createPageEventLink(Security.class, "signIn", email, generatedToken);
	}

	private Link createPageEventLink(final Class<?> pageClass, final String eventType, final Object... context) {
		final String pageName = componentClassResolver.resolvePageClassNameToPageName(pageClass.getName());
		final Page page = requestPageCache.get(pageName);
		return page.getRootElement().createEventLink(eventType, context);
	}

	//todo: move this to template
	private static final String SIGN_UP_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("You or someone else tries to create account on our application using this email address.\n")
			.append("\temail: %s\n")
			.append("\ttoken: %s\n\n")
			.append("If you want to cancel this operation just ignore this message.\n")
			.append("To activate your account please follow the link:\n\n")
			.append("\t%s\n\n")
			.append("---\n%s").toString();

	private static final String RESTORE_PASSWORD_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("You or someone else tries to restore your account password.")
			.append("So new access token was generated for you.\n")
			.append("\temail: %s\n")
			.append("\ttoken: %s\n\n")
			.append("If you want to cancel this operation just ignore this message.\n")
			.append("You can also enter to your account without credentials following the link:\n")
			.append("\t%s\n\n")
			.append("Then you can change your password from account settings page.\n")
			.append("---\n%s").toString();

	private static final String CHANGE_PASSWORD_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("You or someone else tries to change your account password.")
			.append("So new access token was generated for you.\n")
			.append("\temail: %s\n")
			.append("\ttoken: %s\n\n")
			.append("If you want to cancel this changes just ignore this message.\n")
			.append("To apply this changes you should confirm them following the link:\n")
			.append("\t%s\n\n")
			.append("---\n%s").toString();

	private static final String CHANGE_EMAIL_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("You or someone else tries to change your account email address.\n\n")
			.append("\told email: %s\n")
			.append("\tnew email: %s\n")
			.append("\ttoken: %s\n\n")
			.append("If you want to cancel this changes just ignore this message.\n")
			.append("To apply this changes you should confirm them following the link:\n\n")
			.append("\t%s\n\n")
			.append("---\n%s").toString();

	private static final String DEFAULT_SIGNATURE = "BR, Mutabra team";
}
