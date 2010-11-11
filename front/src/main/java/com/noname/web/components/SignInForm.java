package com.noname.web.components;

import com.noname.web.pages.Index;
import com.noname.web.pages.player.hero.HeroSelect;
import com.noname.web.pages.security.SignIn;
import com.noname.web.services.security.GameUser;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.auth.Authentication;
import org.greatage.security.auth.AuthenticationException;
import org.greatage.security.auth.AuthenticationManager;
import org.greatage.security.auth.DefaultAuthenticationToken;
import org.greatage.security.context.UserContext;

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

	@Inject
	private UserContext<GameUser> userContext;

	public boolean isAuthorized() {
		return userContext.getUser() != null;
	}

	public String getUser() {
		return userContext.getUser().getName();
	}

	Object onSignOut() {
		userContext.setUser(null);
		return Index.class;
	}

	Object onSignIn() {
		if (signInForm.isValid()) {
			try {
				final DefaultAuthenticationToken token = new DefaultAuthenticationToken(email, password);
				final Authentication authentication = authenticationManager.authenticate(token);
				userContext.setUser((GameUser) authentication);
				return HeroSelect.class;
			} catch (AuthenticationException e) {
				signInForm.recordError(e.getMessage());
			}
		}
		return SignIn.class;
	}
}
