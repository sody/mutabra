package com.mutabra.security;

import org.greatage.security.AuthenticationToken;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterToken implements AuthenticationToken {
	private final OAuth.Session session;

	public TwitterToken(final OAuth.Session session) {
		this.session = session;
	}

	public OAuth.Session getSession() {
		return session;
	}
}
