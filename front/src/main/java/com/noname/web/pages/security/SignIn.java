package com.noname.web.pages.security;

import com.noname.web.base.pages.AbstractPage;
import com.noname.web.pages.player.hero.HeroSelect;
import com.noname.web.services.security.SecurityService;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
public class SignIn extends AbstractPage {

	@Property
	private String email;

	@Property
	private String password;

	@Property
	private boolean remember;

	@Component
	private Form signInForm;

	@Inject
	private SecurityService securityService;

	Object onSignIn() {
		if (signInForm.isValid()) {
			try {
				securityService.signIn(email, password);
				return HeroSelect.class;
			} catch (RuntimeException ex) {
				signInForm.recordError(ex.getMessage());
				return SignIn.class;
			}
		}
		return null;
	}

	Object onSignUp() {
		return SignUp.class;
	}
}
