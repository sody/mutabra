package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.builder.api.FacebookApi;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookServiceImpl extends BaseOAuthServiceImpl implements FacebookService {

	public FacebookServiceImpl(final String appId, final String appSecret, final String callbackURL, final String scope) {
		super(FacebookApi.class, appId, appSecret, callbackURL, scope);
	}

	@Override
	public String getAuthorizationURL() {
		// request token are not supported
		return service().getAuthorizationUrl(null);
	}

	public Session connect(final String secret) {
		final Token accessToken = service().getAccessToken(null, new Verifier(secret));
		return new SessionImpl(accessToken);
	}

	class SessionImpl implements Session {
		private final Token accessToken;

		SessionImpl(final Token accessToken) {
			this.accessToken = accessToken;
		}

		public Map<String, Object> getProfile() {
			final OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
			signRequest(request);
			final Response response = request.send();

			if (!response.isSuccessful()) {
				throw new OAuthException("Failure sending request");
			}

			final Map<String, Object> profile = new HashMap<String, Object>();
			final JSONObject object = new JSONObject(response.getBody());
			for (String key : object.keys()) {
				profile.put(key, object.get(key));
			}

			System.out.println("FACEBOOK PROFILE: " + object.toString());
			return profile;
		}

		private void signRequest(final OAuthRequest request) {
			service().signRequest(accessToken, request);
		}
	}
}
