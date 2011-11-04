package com.mutabra.web.pages;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.Credentials;
import org.greatage.security.SecurityContext;
import org.greatage.util.StringUtils;

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
		return GameHome.class;
	}

	@OnEvent("signIn")
	Object signInOnce(final String email, final String token) {
		securityContext.signIn(new Credentials("token", email, token));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signUp")
	Object signUp() {
		try {
			accountManager.signUp(email);
			//todo: add success notification
			return Index.class;
		} catch (ValidationException e) {
			signUp.recordError(e.getMessage());
		}
		return null;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "restore")
	Object restorePassword() {
		try {
			accountManager.restorePassword(email);
			//todo: add success notification
			return Index.class;
		} catch (ValidationException e) {
			restore.recordError(e.getMessage());
		}
		return null;
	}

	@OnEvent("confirmEmail")
	Object confirmEmail(final String email, final String token) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(token)) {
			//todo: add failure notification
		} else {
			try {
				accountManager.confirmEmail(email, token);
				//todo: add success notification
			} catch (ValidationException e) {
				//todo: add failure notification
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		return Index.class;
	}
}
