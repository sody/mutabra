package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.builder.api.VkontakteApi;
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
public class VKontakte extends AbstractOAuth2 implements OAuth2 {
    private static final String API_URL = "https://api.vk.com/method/";

    public VKontakte(final String consumerKey,
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
            final OAuthRequest request = new OAuthRequest(Verb.GET, api("getUserInfoEx"));
            final JSONObject result = process(request);

            if (result != null) {
                final JSONObject response = (JSONObject) parse(result, "response");
                if (response != null) {
                    final Map<String, Object> profile = new HashMap<String, Object>();
                    profile.put(ID, parse(response, "user_id"));
                    profile.put(NAME, parse(response, "user_name"));
                    return profile;
                }
            }
            return null;
        }

        public Map<String, Object> getProfile(final String id) {
            return null;
        }
    }
}
