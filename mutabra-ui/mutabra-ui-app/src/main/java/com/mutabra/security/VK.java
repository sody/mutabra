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

	public VK(final String consumerKey, final String consumerSecret) {
		super(VkontakteApi.class, consumerKey, consumerSecret);
	}

	@Override
	protected Session createSession(final OAuthService service, final Token accessToken) {
		return new VKSession(service, accessToken);
	}

	class VKSession extends AbstractOAuthSession implements Session {
		VKSession(final OAuthService service, final Token accessToken) {
			super(service, accessToken);
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
