package com.mutabra.security;

import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Google extends AbstractOAuth implements OAuth {

	public Google(final String consumerKey, final String consumerSecret) {
		super(GoogleApi.class, consumerKey, consumerSecret);
	}

	@Override
	protected Session createSession(final OAuthService service, final Token accessToken) {
		return null;
	}
}
