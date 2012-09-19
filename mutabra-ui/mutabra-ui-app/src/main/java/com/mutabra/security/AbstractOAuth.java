package com.mutabra.security;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractOAuth implements OAuth {
	private final Class<? extends Api> apiClass;
	private final String consumerKey;
	private final String consumerSecret;

	public AbstractOAuth(final Class<? extends Api> apiClass, final String consumerKey, final String consumerSecret) {
		this.apiClass = apiClass;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	public String getAuthorizationUrl(final String callbackUrl, final String scope) {
		final OAuthService service = service(callbackUrl, scope);
		return service.getAuthorizationUrl(getRequestToken(service));
	}

	public Session connect(final String token, final String secret, final String callbackUrl, final String scope) {
		final OAuthService service = service(callbackUrl, scope);
		final Token accessToken = getAccessToken(service, token, secret);
		return createSession(service, accessToken);
	}

	public Session connect() {
		final OAuthService service = service(null, null);
		return createSession(service, null);
	}

	protected Token getRequestToken(final OAuthService service) {
		return service.getRequestToken();
	}

	protected Token getAccessToken(final OAuthService service, final String token, final String secret) {
		final Token requestToken = new Token(token, secret);
		return service.getAccessToken(requestToken, new Verifier(requestToken.getSecret()));
	}

	protected abstract Session createSession(OAuthService service, Token accessToken);

	protected OAuthService service(final String callbackUrl, final String scope) {
		final ServiceBuilder builder = new ServiceBuilder()
				.provider(apiClass)
				.apiKey(consumerKey).apiSecret(consumerSecret);
		if (callbackUrl != null) {
			builder.callback(callbackUrl);
		}
		if (scope != null) {
			builder.scope(scope);
		}
		return builder.build();
	}
}
