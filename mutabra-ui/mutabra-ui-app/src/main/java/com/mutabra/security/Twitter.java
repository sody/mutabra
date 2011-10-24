package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.builder.api.TwitterApi;
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
public class Twitter extends AbstractOAuth implements OAuth {

	public Twitter(final String consumerKey, final String consumerSecret) {
		super(TwitterApi.class, consumerKey, consumerSecret);
	}

	@Override
	protected Session createSession(final OAuthService service, final Token accessToken) {
		return new TwitterSession(service, accessToken);
	}

	class TwitterSession extends AbstractOAuthSession implements Session {
		TwitterSession(final OAuthService service, final Token accessToken) {
			super(service, accessToken);
		}

		public Map<String, Object> getProfile() {
			final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/account/verify_credentials.json");

			final JSONObject result = process(request);
			if (result != null) {
				final Map<String, Object> profile = new HashMap<String, Object>();
				profile.put("id", parse(result, "id"));
				profile.put("name", parse(result, "name"));
				profile.put("language", parse(result, "language"));
				return profile;
			}
			return null;
		}

		public Map<String, Object> getProfile(final String id) {

			return null;
		}
	}
}
