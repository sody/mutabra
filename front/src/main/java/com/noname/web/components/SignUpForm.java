package com.noname.web.components;

import com.noname.services.security.AccountService;
import com.noname.web.pages.security.SignUp;
import com.noname.web.services.security.GameUser;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.context.UserContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignUpForm {

	@Property
	private String email;

	@Component
	private Form signUpForm;

	@Inject
	private AccountService accountService;

	@Inject
	private UserContext<GameUser> userContext;

	public boolean isAuthorized() {
		return userContext.getUser() != null;
	}

	Object onSignUp() {
		if (signUpForm.isValid()) {
			accountService.createAccount(email);
		}
		return SignUp.class;
	}
}
