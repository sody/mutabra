package com.mutabra.security;

import org.scribe.builder.api.FacebookApi;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookServiceImpl extends BaseOAuthServiceImpl implements FacebookService {

	public FacebookServiceImpl(final String appId, final String appSecret, final String callbackURL) {
		super(FacebookApi.class, appId, appSecret, callbackURL);
	}

	@Override
	public String getAuthorizationURL() {
		// request token are not supported
		return service().getAuthorizationUrl(null);
	}
}
