package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Google extends AbstractOAuth2 implements OAuth2 {

	public Google(final String consumerKey, final String consumerSecret) {
		super(GoogleApi20.class, consumerKey, consumerSecret);
	}

	@Override
	protected Session createSession(final OAuthService service, final Token accessToken) {
		return new GoogleSession(service, accessToken);
	}

	class GoogleSession extends AbstractOAuthSession implements Session {
		GoogleSession(final OAuthService service, final Token accessToken) {
			super(service, accessToken);
		}

		public Map<String, Object> getProfile() {
			final OAuthRequest request = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v2/userinfo");
			final JSONObject result = process(request);

			if (result != null) {
				final Map<String, Object> profile = new HashMap<String, Object>();
				profile.put("id", parse(result, "id"));
				profile.put("email", parse(result, "email"));
				profile.put("name", parse(result, "name"));
				profile.put("locale", parse(result, "locale"));
				return profile;
			}
			return null;
		}

		public Map<String, Object> getProfile(final String id) {
			return null;
		}
	}
}
