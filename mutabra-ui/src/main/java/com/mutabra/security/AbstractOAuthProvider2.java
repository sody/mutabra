package com.mutabra.security;

import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractOAuthProvider2 extends AbstractOAuthProvider {

    protected AbstractOAuthProvider2(final Class<? extends Api> apiClass,
                                     final String consumerKey,
                                     final String consumerSecret,
                                     final String redirectUri) {
        super(apiClass, consumerKey, consumerSecret, redirectUri);
    }

    @Override
    protected Token getRequestToken(final OAuthService service) {
        return null;
    }

    @Override
    protected Token getAccessToken(final OAuthService service, final String token, final String secret) {
        return service.getAccessToken(null, new Verifier(secret));
    }
}
