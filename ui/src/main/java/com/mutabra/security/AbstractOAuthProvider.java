/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.security;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractOAuthProvider implements OAuthProvider {
    private final Class<? extends Api> apiClass;
    private final String consumerKey;
    private final String consumerSecret;
    private final String callbackUrl;

    public AbstractOAuthProvider(final Class<? extends Api> apiClass,
                                 final String consumerKey,
                                 final String consumerSecret,
                                 final String callbackUrl) {
        this.apiClass = apiClass;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.callbackUrl = callbackUrl;
    }

    public String getAuthorizationUrl(final String scope) {
        final OAuthService service = service(scope);
        return service.getAuthorizationUrl(getRequestToken(service));
    }

    public Session connect(final String token, final String secret) {
        final OAuthService service = service();
        final Token accessToken = getAccessToken(service, token, secret);
        return createSession(service, accessToken);
    }

    public Session connect() {
        final OAuthService service = service();
        return createSession(service, null);
    }

    protected Token getRequestToken(final OAuthService service) {
        return service.getRequestToken();
    }

    protected Token getAccessToken(final OAuthService service, final String token, final String secret) {
        final Token requestToken = new Token(token, secret);
        return service.getAccessToken(requestToken, new Verifier(requestToken.getSecret()));
    }

    protected abstract Session createSession(OAuthService service, Token accessToken);

    protected OAuthService service() {
        return service(null);
    }

    protected OAuthService service(final String scope) {
        final ServiceBuilder builder = new ServiceBuilder()
                .provider(apiClass)
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .callback(callbackUrl);
        if (scope != null) {
            builder.scope(scope);
        }
        return builder.build();
    }
}
