package com.noname.web.components.security;

import com.noname.game.User;
import com.noname.services.security.UserService;
import com.noname.web.pages.Index;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignOutLink {

	@Inject
	private SecurityContext securityContext;

	@Inject
	private UserService userService;

	Object onSignOut() {
		final User user = (User) securityContext.getAuthentication();
		userService.removeUser(user);
		securityContext.setAuthentication(null);
		return Index.class;
	}

}
