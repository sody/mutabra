package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface FacebookService extends BaseOAuthService {

	JSONObject getProfile(String secret);
}
