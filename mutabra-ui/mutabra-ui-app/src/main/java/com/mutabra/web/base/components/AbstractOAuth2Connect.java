package com.mutabra.web.base.components;

import com.mutabra.security.OAuth;
import com.mutabra.security.OAuth2;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractOAuth2Connect extends AbstractOAuthConnect {

	@Override
	protected abstract OAuth2 getOAuth();

	@Override
	protected OAuth.Session startSession(final String token, final String secret) {
		return getOAuth().connect(secret, getRedirectUri(), getScope());
	}

	protected Object doConnected(final String secret, final String error) {
		return super.doConnected(null, secret, error);
	}
}
