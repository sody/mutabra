package com.mutabra.security;

import org.greatage.security.AuthenticationToken;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookToken implements AuthenticationToken {
	private final String code;

	public FacebookToken(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
