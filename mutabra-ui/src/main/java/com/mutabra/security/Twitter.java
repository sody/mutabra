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
    private static final String API_URL = "https://api.twitter.com/1.1/";

    public Twitter(final String consumerKey,
                   final String consumerSecret,
                   final String callbackUrl) {
        super(TwitterApi.class, consumerKey, consumerSecret, callbackUrl);
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
            final OAuthRequest request = new OAuthRequest(Verb.GET, api("account/verify_credentials.json"));

            final JSONObject result = process(request);
            if (result != null) {
                final Map<String, Object> profile = new HashMap<String, Object>();
                profile.put(ID, parse(result, "id"));
                profile.put(NAME, parse(result, "name"));
                profile.put(LOCALE, parse(result, "language"));
                return profile;
            }
            return null;
        }

        public Map<String, Object> getProfile(final String id) {
            return null;
        }
    }
}
