package com.mutabra.web.pages;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.services.AccountManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecurityContext;

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
	private AccountManager accountManager;

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

		accountManager.signUp(email);

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

		accountManager.restorePassword(email);

		return Index.class;
	}
}
