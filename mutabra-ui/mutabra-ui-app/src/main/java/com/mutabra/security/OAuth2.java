package com.mutabra.security;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface OAuth2 extends OAuth {

	String getAuthorizationUrl(String redirectUri, String scope);

	Session connect(String code, String redirectUri, String scope);

}
