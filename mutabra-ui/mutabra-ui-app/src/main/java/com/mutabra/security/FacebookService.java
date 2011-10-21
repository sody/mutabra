package com.mutabra.security;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface FacebookService extends BaseOAuthService {

	Session connect(String secret);

	interface Session {

		Map<String, Object> getProfile();
	}
}
