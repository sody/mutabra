package com.mutabra.security;

import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractOAuth2 extends AbstractOAuth implements OAuth2 {

	protected AbstractOAuth2(final Class<? extends Api> apiClass, final String consumerKey, final String consumerSecret) {
		super(apiClass, consumerKey, consumerSecret);
	}

	public Session connect(final String code, final String redirectUri, final String scope) {
		return connect(null, code, redirectUri, scope);
	}

	@Override
	protected Token getRequestToken(final OAuthService service) {
		return null;
	}

	protected Token getAccessToken(final OAuthService service, final Token authorizedRequestToken) {
		return service.getAccessToken(null, new Verifier(authorizedRequestToken.getSecret()));
	}
}
