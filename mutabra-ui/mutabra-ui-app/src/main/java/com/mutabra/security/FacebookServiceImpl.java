package com.mutabra.security;

import org.apache.tapestry5.json.JSONObject;
import org.scribe.builder.api.FacebookApi;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.*;

import javax.servlet.http.HttpServletResponse;
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

	public JSONObject getProfile(final String secret) {
		final Token accessToken = getAccessToken(secret);
		final OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
		service().signRequest(accessToken, request);
		final Response response = request.send();

		if (!response.isSuccessful()) {
			throw new OAuthException("Failure sending request");
		}

		return new JSONObject(response.getBody());
	}

	private Token getAccessToken(final String secret) {
		return service().getAccessToken(null, new Verifier(secret));
	}
}
