package com.noname.web.pages.security;

import com.noname.web.base.pages.AbstractPage;
import com.noname.web.services.security.SecurityService;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignUp extends AbstractPage {

	@Property
	private String email;

	@Component
	private Form signUpForm;

	@Inject
	private SecurityService securityService;

	Object onSignUp() {
		if (signUpForm.isValid()) {
			try {
				securityService.signUp(email);
			} catch (RuntimeException ex) {
				signUpForm.recordError(ex.getMessage());
				return SignUp.class;
			}
		}
		return null;
	}

	Object onSignIn() {
		return SignIn.class;
	}
}
