package com.noname.web.services;

import org.apache.tapestry5.services.*;
import org.greatage.security.context.UserContext;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserPersistenceFilter implements RequestFilter {
	private static final String SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT";

	private final UserContext<GameUser> userContext;

	public UserPersistenceFilter(final UserContext<GameUser> userContext) {
		this.userContext = userContext;
	}

	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		try {
			final GameUser user = getUser(request);
			userContext.setUser(user);
			return handler.service(request, response);
		} finally {
			final GameUser user = userContext.getUser();
			userContext.setUser(null);
			setUser(request, user);
		}
	}

	private GameUser getUser(final Request request) {
		final Session session = getSession(request, true);
		final Object user = session != null ? session.getAttribute(SECURITY_CONTEXT_KEY) : null;
		return user != null && user instanceof GameUser ? (GameUser) user : null;
	}

	private void setUser(final Request request, final GameUser user) {
		final Session session = getSession(request, true);
		session.setAttribute(SECURITY_CONTEXT_KEY, user);
	}

	private Session getSession(final Request request, final boolean allowCreate) {
		final Session session = request.getSession(false);
		return session == null && allowCreate ? request.getSession(true) : session;
	}
}
