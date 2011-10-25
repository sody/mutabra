package com.mutabra.web.internal;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.pages.Security;
import com.mutabra.web.services.AccountManager;
import com.mutabra.web.services.MailService;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.RequestPageCache;
import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.greatage.security.SecretEncoder;

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

	public void signUp(final String email) {
		final String generatedToken = Authorities.generateSecret();
		final String generatedPassword = Authorities.generateSecret();

		final Account account = accountService.create();
		account.setEmail(email);
		account.setPendingPassword(passwordEncoder.encode(generatedPassword));
		account.setToken(generatedToken);
		account.setRegistered(new Date());

		final Link link = createTokenLink(email, generatedToken);
		final String message = String.format(REGISTRATION_NOTIFICATION, email, generatedToken, link, DEFAULT_SIGNATURE);
		mailService.send(email, "Mutabra Account", message);
		accountService.save(account);
	}

	public void restorePassword(final String email) {
		final Account account = accountService.query().withEmail(email).unique();

		final String generatedToken = Authorities.generateSecret();
		account.setToken(generatedToken);

		final Link link = createTokenLink(email, generatedToken);
		final String message = String.format(RESTORE_PASSWORD_NOTIFICATION, email, generatedToken, link, DEFAULT_SIGNATURE);
		mailService.send(email, "Mutabra Account", message);
		accountService.save(account);
	}

	public void changePassword(final String email, final String password) {
		final Account account = accountService.query().withEmail(email).unique();

		final String generatedToken = Authorities.generateSecret();
		account.setToken(generatedToken);
		account.setPendingPassword(passwordEncoder.encode(password));

		final Link link = createTokenLink(email, generatedToken);
		final String message = String.format(CHANGE_PASSWORD_NOTIFICATION, email, generatedToken, link.toAbsoluteURI(), DEFAULT_SIGNATURE);
		mailService.send(email, "Mutabra Account", message);
		accountService.save(account);
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
	private static final String REGISTRATION_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("New account was created for you.\n")
			.append("\tlogin: %s\n\tactivation token: %s\n\n")
			.append("To activate your account please follow the link:\n\n\t%s\n\n")
			.append("---\n%s").toString();

	private static final String RESTORE_PASSWORD_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("New access token was created for you.\n")
			.append("\tlogin: %s\n\tactivation token: %s\n\n")
			.append("To enter your account please follow the link:\n\n\t%s\n\n")
			.append("---\n%s").toString();

	private static final String CHANGE_PASSWORD_NOTIFICATION = new StringBuilder()
			.append("Hello Mr.,\n\n")
			.append("New access token was created for you.\n")
			.append("\tlogin: %s\n\tactivation token: %s\n\n")
			.append("To confirm password change follow the link:\n\n\t%s\n\n")
			.append("---\n%s").toString();

	private static final String DEFAULT_SIGNATURE = "BR, Mutabra team";
}
