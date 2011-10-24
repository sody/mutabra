package com.mutabra.security;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface OAuth {

	String getAuthorizationUrl(String callbackUrl, String scope);

	Session connect(String requestToken, String requestTokenSecret, String callbackUrl, String scope);

	Session connect();

	interface Session {

		Map<String, Object> getProfile();

		Map<String, Object> getProfile(String id);

	}
}
