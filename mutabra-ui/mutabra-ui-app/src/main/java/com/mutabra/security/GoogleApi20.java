package com.mutabra.security;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleApi20 extends DefaultApi20 {
	private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&response_type=code";
	private static final String SCOPED_AUTHORIZE_URL = String.format("%s&scope=%%s", AUTHORIZE_URL);

	private static final String ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";

	@Override
	public String getAccessTokenEndpoint() {
		return ACCESS_TOKEN_URL;
	}

	@Override
	public Verb getAccessTokenVerb() {
		return Verb.POST;
	}

	@Override
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new AccessTokenExtractor() {
			public Token extract(final String response) {
				Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");

				final Matcher matcher = Pattern.compile("\"access_token\" : \"([^&\"]+)\"").matcher(response);
				if (matcher.find()) {
					String token = OAuthEncoder.decode(matcher.group(1));
					return new Token(token, "", response);
				} else {
					throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null);
				}
			}
		};
	}

	@Override
	public String getAuthorizationUrl(final OAuthConfig config) {
		Preconditions.checkValidUrl(config.getCallback(),
				"Must provide a valid url as callback. Facebook does not support OOB");
		// Append scope if present
		if (config.hasScope()) {
			return String.format(SCOPED_AUTHORIZE_URL,
					config.getApiKey(),
					OAuthEncoder.encode(config.getCallback()),
					OAuthEncoder.encode(config.getScope()));
		} else {
			return String.format(AUTHORIZE_URL,
					config.getApiKey(),
					OAuthEncoder.encode(config.getCallback()));
		}
	}

	@Override
	public OAuthService createService(final OAuthConfig config) {
		return new GoogleOAuth2Service(this, config);
	}

	private class GoogleOAuth2Service extends OAuth20ServiceImpl {
		private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
		private static final String GRANT_TYPE = "grant_type";

		private DefaultApi20 api;
		private OAuthConfig config;

		public GoogleOAuth2Service(final DefaultApi20 api, final OAuthConfig config) {
			super(api, config);
			this.api = api;
			this.config = config;
		}

		@Override
		public Token getAccessToken(final Token requestToken, final Verifier verifier) {
			final OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
			request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
			request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
			request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
			request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
			request.addBodyParameter(GRANT_TYPE, GRANT_TYPE_AUTHORIZATION_CODE);
			final Response response = request.send();
			return api.getAccessTokenExtractor().extract(response.getBody());
		}
	}
}
