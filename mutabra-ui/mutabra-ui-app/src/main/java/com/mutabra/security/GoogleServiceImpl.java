package com.mutabra.security;

import org.scribe.builder.api.GoogleApi;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleServiceImpl extends BaseOAuthServiceImpl implements GoogleService {

	public GoogleServiceImpl(final String apiKey, final String apiSecret, final String callbackURL, final String scope) {
		super(GoogleApi.class, apiKey, apiSecret, callbackURL, scope);
	}
}
