package com.mutabra.web.internal;

import org.apache.tapestry5.services.*;
import org.greatage.security.Authentication;
import org.greatage.security.SecurityContext;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityPersistenceFilter implements RequestFilter {
	private static final String SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT";

	private final SecurityContext securityContext;

	public SecurityPersistenceFilter(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	@SuppressWarnings({"unchecked"})
	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		try {
			final Authentication authentication = loadAuthentication(request);
			securityContext.setCurrentUser(authentication);
			return handler.service(request, response);
		} finally {
			final Authentication authentication = securityContext.getCurrentUser();
			saveAuthentication(request, authentication);
		}
	}

	private Authentication loadAuthentication(final Request request) {
		final Session session = getSession(request, true);
		final Object user = session != null ? session.getAttribute(SECURITY_CONTEXT_KEY) : null;
		return user != null && user instanceof Authentication ? (Authentication) user : null;
	}

	private void saveAuthentication(final Request request, final Authentication user) {
		final Session session = getSession(request, true);
		session.setAttribute(SECURITY_CONTEXT_KEY, user);
	}

	private Session getSession(final Request request, final boolean allowCreate) {
		final Session session = request.getSession(false);
		return session == null && allowCreate ? request.getSession(true) : session;
	}
}
