package com.mutabra.security;

import org.scribe.builder.api.VkontakteApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class VKontakte extends AbstractOAuth2 implements OAuth2 {

	public VKontakte(final String consumerKey, final String consumerSecret) {
		super(VkontakteApi.class, consumerKey, consumerSecret);
	}

	@Override
	protected Session createSession(final OAuthService service, final Token accessToken) {
		return null;
	}
}
