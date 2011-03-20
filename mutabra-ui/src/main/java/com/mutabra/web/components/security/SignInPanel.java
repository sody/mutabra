package com.mutabra.web.components.security;

import com.mutabra.web.pages.security.SignUp;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignInPanel extends SignInForm {

	Object onSignUp() {
		return SignUp.class;
	}

}
