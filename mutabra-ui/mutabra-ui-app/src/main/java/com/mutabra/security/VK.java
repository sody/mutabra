package com.mutabra.security;

import org.scribe.builder.api.VkontakteApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class VK extends AbstractOAuth2 implements OAuth2 {
	private static final String API_URL = "https://api.vk.com/method/";

	public VK(final String consumerKey,
			  final String consumerSecret,
			  final String redirectUri) {
		super(VkontakteApi.class, consumerKey, consumerSecret, redirectUri);
	}

	@Override
	protected OAuth.Session createSession(final OAuthService service, final Token accessToken) {
		return new Session(service, accessToken);
	}

	class Session extends AbstractOAuthSession {
		Session(final OAuthService service, final Token accessToken) {
			super(service, accessToken, API_URL);
		}

		public Map<String, Object> getProfile() {
//			final OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
//			final JSONObject result = process(request);
//
//			if (result != null) {
//				final Map<String, Object> profile = new HashMap<String, Object>();
//				profile.put("id", parse(result, "id"));
//				profile.put("email", parse(result, "email"));
//				profile.put("name", parse(result, "name"));
//				profile.put("locale", parse(result, "locale"));
//				return profile;
//			}
			return null;
		}

		public Map<String, Object> getProfile(final String id) {
			return null;
		}
	}
}
