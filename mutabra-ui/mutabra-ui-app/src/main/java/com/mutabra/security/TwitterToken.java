package com.mutabra.security;

import org.greatage.security.AuthenticationToken;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterToken implements AuthenticationToken {
	private final String token;
	private final String secret;
	private final String callbackUrl;
	private final String scope;

	public TwitterToken(final String token, final String secret, final String callbackUrl, final String scope) {
		this.token = token;
		this.secret = secret;
		this.callbackUrl = callbackUrl;
		this.scope = scope;
	}

	public String getToken() {
		return token;
	}

	public String getSecret() {
		return secret;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public String getScope() {
		return scope;
	}
}
