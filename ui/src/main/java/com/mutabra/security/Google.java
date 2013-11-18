/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

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
public class Google extends AbstractOAuthProvider2 {
    private static final String API_URL = "https://www.googleapis.com/oauth2/v2/";

    public Google(final String consumerKey,
                  final String consumerSecret,
                  final String redirectUri) {
        super(GoogleApi20.class, consumerKey, consumerSecret, redirectUri);
    }

    @Override
    protected OAuthProvider.Session createSession(final OAuthService service, final Token accessToken) {
        return new Session(service, accessToken);
    }

    class Session extends AbstractOAuthSession {
        Session(final OAuthService service, final Token accessToken) {
            super(service, accessToken, API_URL);
        }

        public Map<String, Object> getProfile() {
            final OAuthRequest request = new OAuthRequest(Verb.GET, api("userinfo"));
            final JSONObject result = process(request);

            if (result != null) {
                final Map<String, Object> profile = new HashMap<String, Object>();
                profile.put(ID, parse(result, "id"));
                profile.put(EMAIL, parse(result, "email"));
                profile.put(NAME, parse(result, "name"));
                profile.put(LOCALE, parse(result, "locale"));
                return profile;
            }
            return null;
        }

        public Map<String, Object> getProfile(final String id) {
            return null;
        }
    }
}
