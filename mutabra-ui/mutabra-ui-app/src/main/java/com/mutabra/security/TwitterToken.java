package com.mutabra.security;

import org.greatage.security.AuthenticationToken;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterToken implements AuthenticationToken {
	private final String token;
	private final String secret;

	public TwitterToken(final String token, final String secret) {
		this.token = token;
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public String getSecret() {
		return secret;
	}
}
