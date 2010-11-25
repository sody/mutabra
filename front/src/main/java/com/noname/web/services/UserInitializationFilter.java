package com.noname.web.services;

import com.noname.game.User;
import com.noname.services.security.UserService;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.greatage.security.SecurityContext;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserInitializationFilter implements RequestFilter {
	private final UserService userService;
	private final SecurityContext securityContext;

	public UserInitializationFilter(final UserService userService, final SecurityContext securityContext) {
		this.userService = userService;
		this.securityContext = securityContext;
	}

	@Override
	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		final User user = (User) securityContext.getAuthentication();
		if (user != null) {
			userService.updateUser(user);
		}

		return handler.service(request, response);
	}
}
