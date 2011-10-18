package com.mutabra.web.internal;

import org.apache.tapestry5.services.*;
import org.greatage.security.Authentication;
import org.greatage.security.SecurityContext;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

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
			return securityContext.doAs(authentication, new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws IOException {
					final boolean result = handler.service(request, response);
					saveAuthentication(request, securityContext.getCurrentUser());
					return result;
				}
			});
		} catch (IOException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// it will never happen
			throw new SecurityException(e);
		}
	}

	private Authentication loadAuthentication(final Request request) {
		final Session session = getSession(request, true);
		final Object authentication = session != null ? session.getAttribute(SECURITY_CONTEXT_KEY) : null;
		return authentication != null && authentication instanceof Authentication ? (Authentication) authentication : null;
	}

	private void saveAuthentication(final Request request, final Authentication authentication) {
		final Session session = getSession(request, true);
		session.setAttribute(SECURITY_CONTEXT_KEY, authentication);
	}

	private Session getSession(final Request request, final boolean allowCreate) {
		final Session session = request.getSession(false);
		return session == null && allowCreate ? request.getSession(true) : session;
	}
}
