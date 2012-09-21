package com.mutabra.web.pages.account;

import com.mutabra.domain.game.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.Security;
import com.mutabra.web.services.AccountContext;
import com.mutabra.web.services.MailService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresAuthentication
public class AccountSettings extends AbstractPage {
	private static final RandomNumberGenerator GENERATOR = new SecureRandomNumberGenerator();

	@Property
	private String email;

	@Property
	private String password;

	@Property
	private String confirmPassword;

	@InjectComponent
	private Form changePasswordForm;

	@InjectPage
	private Security security;

	@Inject
	private AccountContext accountContext;

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

	@Inject
	private HashService hashService;

	@Inject
	private MailService mailService;

	public Account getValue() {
		return accountContext.getAccount();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "accountForm")
	void save() {
		accountService.saveOrUpdate(getValue());
		//todo: add success notification
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "changeEmailForm")
	void changeEmail() {
		//todo: validate if if email exist, if new email differs from current and if new email are not exist
//		accountManager.changeEmail(getValue().getEmail(), email);
		//todo: add success notification
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "changePasswordForm")
	void changePassword() {
		final Account account = getValue();

		if (account == null || account.getEmail() == null || account.getToken() != null) {
			changePasswordForm.recordError(message("error.change-password"));
			return;
		}

		final String token = generateSecret();
		final Hash hash = generateHash(password);

		account.setToken(token);
		account.setPendingPassword(hash.toBase64());
		account.setPendingSalt(hash.getSalt().toBase64());

		final Link link = security.createApplyChangesLink(account.getEmail(), token);
		mailService.send(
				account.getEmail(),
				message("mail.change-password.title"),
				format("mail.change-password.body", link.toAbsoluteURI()));
		accountService.saveOrUpdate(account);
	}

	private Hash generateHash(final String secret) {
		return hashService.computeHash(new HashRequest.Builder().setSource(secret).build());
	}

	private String generateSecret() {
		return GENERATOR.nextBytes().toBase64();
	}
}
