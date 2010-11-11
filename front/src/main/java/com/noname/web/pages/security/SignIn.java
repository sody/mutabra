package com.noname.web.pages.security;

import com.noname.web.components.SignInForm;

/**
 * @author Ivan Khalopik
 */
public class SignIn extends SignInForm {

	public String getTitle() {
		return null;
	}

	public Object onSignUp() {
		return SignUp.class;
	}
}
