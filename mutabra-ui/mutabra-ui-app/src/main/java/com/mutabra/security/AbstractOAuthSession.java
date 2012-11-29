package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractOAuthSession implements OAuth.Session {
    private final OAuthService service;
    private final Token accessToken;
    private final String apiUri;

    protected AbstractOAuthSession(final OAuthService service, final Token accessToken, final String apiUri) {
        this.service = service;
        this.accessToken = accessToken;
        this.apiUri = apiUri;
    }

    protected OAuthService service() {
        return service;
    }

    protected Token accessToken() {
        return accessToken;
    }

    protected JSONObject process(final OAuthRequest request) {
        final Response response = send(request);

        if (response.isSuccessful()) {
            return new JSONObject(response.getBody());
        }
        return null;
    }

    protected Response send(final OAuthRequest request) {
        if (accessToken != null) {
            service.signRequest(accessToken, request);
        }
        return request.send();
    }

    protected Object parse(final JSONObject object, final String key) {
        return object.has(key) ? object.get(key) : null;
    }

    protected String api(final String uri) {
        return apiUri + uri;
    }
}
