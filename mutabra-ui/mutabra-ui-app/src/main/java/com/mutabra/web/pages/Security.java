package com.mutabra.web.pages;

import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountManager;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
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
	private Form signInForm;

	@InjectComponent
	private Form restoreForm;

	@Inject
	private AccountManager accountManager;

	@OnEvent(value = EventConstants.SUCCESS, component = "signInForm")
	Object signIn() {
		getSubject().login(new UsernamePasswordToken(email, password));
		return GameHome.class;
	}

	@OnEvent("signIn")
	Object signInOnce(final String email, final String token) {
//		securityContext.signIn(new Credentials("token", email, token));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "restoreForm")
	Object restorePassword() {
		try {
			accountManager.restorePassword(email);
			//todo: add success notification
			return Index.class;
		} catch (ValidationException e) {
			restoreForm.recordError(e.getMessage());
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
