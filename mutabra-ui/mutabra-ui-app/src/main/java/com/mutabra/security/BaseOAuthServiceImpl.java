package com.mutabra.security;


import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseOAuthServiceImpl implements BaseOAuthService {
	private final OAuthService service;

	public BaseOAuthServiceImpl(final Class<? extends Api> provider, final String apiKey, final String apiSecret, final String callbackURL) {
		this(provider, apiKey, apiSecret, callbackURL, null);
	}

	public BaseOAuthServiceImpl(final Class<? extends Api> provider, final String apiKey, final String apiSecret,
								final String callbackURL, final String scope) {
		final ServiceBuilder builder = new ServiceBuilder()
				.provider(provider)
				.apiKey(apiKey)
				.apiSecret(apiSecret)
				.callback(callbackURL);

		if (scope != null) {
			builder.scope(scope);
		}
		service = builder.build();
	}

	public String getAuthorizationURL() {
		return service.getAuthorizationUrl(service.getRequestToken());
	}

	protected OAuthService service() {
		return service;
	}
}
