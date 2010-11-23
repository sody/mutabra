package com.noname.web.components.security;

import com.noname.web.pages.security.SignUp;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignInPanel extends SignInForm {

	Object onSignUp() {
		return SignUp.class;
	}

}
