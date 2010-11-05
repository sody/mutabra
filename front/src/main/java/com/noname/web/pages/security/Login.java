package com.noname.web.pages.security;

import com.noname.web.pages.Index;
import com.noname.web.services.GameUser;
import ga.security.auth.Authentication;
import ga.security.auth.AuthenticationException;
import ga.security.auth.AuthenticationManager;
import ga.security.auth.DefaultAuthenticationToken;
import ga.security.context.UserContext;
import ga.tapestry.commonlib.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

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
