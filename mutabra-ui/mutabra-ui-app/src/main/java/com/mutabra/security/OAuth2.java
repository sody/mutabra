package com.mutabra.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface OAuth2 extends OAuth {

	String getAuthorizationUrl(String redirectUri, String scope);

	Session connect(String code, String redirectUri, String scope);

}
