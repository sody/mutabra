package com.noname.web.pages;

import com.noname.web.base.pages.AbstractPage;
import com.noname.web.pages.security.SignUp;
import com.noname.web.services.SecurityService;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Index extends AbstractPage {

	@Property
	private String email;

	@Component
	private Form signUpForm;

	@Inject
	private SecurityService securityService;

	@Inject
	@Path("gomer.jpg")
	private Asset logo;

	public Asset getLogo() {
		return logo;
	}

	public boolean isAuthenticated() {
		return securityService.isAuthenticated();
	}

	Object onSignUp() {
		if (signUpForm.isValid()) {
			try {
				securityService.signUp(email);
			} catch (RuntimeException ex) {
				return SignUp.class;
			}
		}
		return null;
	}
}
