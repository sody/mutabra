package com.noname.web.pages.security;

import com.noname.web.base.pages.AbstractPage;
import com.noname.web.pages.Index;
import com.noname.web.services.GameUser;
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
 */
public class Login extends AbstractPage {

	@Property
	private String email;

	@Property
	private String password;

	@Component
	private Form loginForm;

	@Inject
	private AuthenticationManager authenticationManager;

	@Inject
	private UserContext<GameUser> userContext;

	Class onLogin() {
		if (loginForm.isValid()) {
			try {
				final DefaultAuthenticationToken token = new DefaultAuthenticationToken(email, password);
				final Authentication authentication = authenticationManager.authenticate(token);
				userContext.setUser((GameUser) authentication);
				return Index.class;
			} catch (AuthenticationException e) {
				loginForm.recordError(e.getMessage());
			}
		}
		return null;
	}
}
