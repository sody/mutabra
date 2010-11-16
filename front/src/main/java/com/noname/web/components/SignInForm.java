package com.noname.web.components;

import com.noname.web.pages.player.hero.HeroSelect;
import com.noname.web.pages.security.SignIn;
import com.noname.web.services.SecurityService;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignInForm {

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
				return SignIn.class;
			}
		}
		return null;
	}
}
