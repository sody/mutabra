package com.noname.web.components.security;

import com.noname.web.pages.security.SignIn;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignUpPanel extends SignUpForm {

	Object onSignIn() {
		return SignIn.class;
	}
}
