package com.mutabra.web.internal.security;

import com.mutabra.security.OAuth;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookToken implements AuthenticationToken {
	private final OAuth.Session session;

	public FacebookToken(final OAuth.Session session) {
		this.session = session;
	}

	public OAuth.Session getSession() {
		return session;
	}

	public Object getPrincipal() {
		return session.getProfile();
	}

	public Object getCredentials() {
		return session;
	}
}
