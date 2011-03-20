package com.mutabra.web.components.security;

import com.mutabra.web.pages.security.SignIn;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignUpPanel extends SignUpForm {

	Object onSignIn() {
		return SignIn.class;
	}
}
