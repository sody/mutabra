package com.mutabra.security;

import org.greatage.security.AuthenticationToken;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OneTimeToken implements AuthenticationToken {
	private final String name;
	private final String token;

	public OneTimeToken(final String name, final String token) {
		this.name = name;
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}
}
