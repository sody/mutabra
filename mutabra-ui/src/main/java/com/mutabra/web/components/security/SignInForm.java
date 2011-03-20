package com.mutabra.web.components.security;

import com.mutabra.web.pages.player.hero.HeroSelect;
import com.mutabra.web.pages.security.SignIn;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.AuthenticationManager;
import org.greatage.security.PasswordAuthenticationToken;

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
				final PasswordAuthenticationToken token = new PasswordAuthenticationToken(email, password);
				authenticationManager.signIn(token);
				return HeroSelect.class;
			} catch (RuntimeException ex) {
				signInForm.recordError(ex.getMessage());
				return SignIn.class;
			}
		}
		return null;
	}
}
