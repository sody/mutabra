package com.noname.web.components.security;

import com.noname.web.pages.player.hero.HeroSelect;
import com.noname.web.pages.security.SignIn;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.AuthenticationManager;
import org.greatage.security.DefaultAuthenticationToken;

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
	private AuthenticationManager authenticationManager;

	Object onSignIn() {
		if (signInForm.isValid()) {
			try {
				final DefaultAuthenticationToken token = new DefaultAuthenticationToken(email, password);
				authenticationManager.authenticate(token);
				return HeroSelect.class;
			} catch (RuntimeException ex) {
				signInForm.recordError(ex.getMessage());
				return SignIn.class;
			}
		}
		return null;
	}
}
