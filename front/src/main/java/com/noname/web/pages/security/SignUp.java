package com.noname.web.pages.security;

import com.noname.web.components.SignUpForm;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignUp extends SignUpForm {

	public Object onSignIn() {
		return SignIn.class;
	}

}
