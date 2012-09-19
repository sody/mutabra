package com.mutabra.web.internal.security;

import com.mutabra.security.OAuth;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OAuthToken implements AuthenticationToken {
	private final OAuth.Session session;

	public OAuthToken(final OAuth.Session session) {
		this.session = session;
	}

	public OAuth.Session getSession() {
		return session;
	}

	public Object getPrincipal() {
		if (session != null) {
			final Map<String, Object> profile = session.getProfile();
			if (profile != null && profile.containsKey("id")) {
				return profile.get("id");
			}
		}
		return null;
	}

	public Object getCredentials() {
		return session;
	}
}
