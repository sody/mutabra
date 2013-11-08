/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.security;

import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.security.OAuthProvider;
import com.mutabra.web.services.OAuthSource;

import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class OAuthSourceImpl implements OAuthSource {
    private final Map<AccountCredentialType, OAuthProvider> providers;

    public OAuthSourceImpl(final Map<AccountCredentialType, OAuthProvider> providers) {
        this.providers = providers;
    }

    @Override
    public String getAuthorizationUrl(final AccountCredentialType type,
                                      final String scope) {
        final OAuthProvider provider = providers.get(type);
        return provider != null ? provider.getAuthorizationUrl(scope) : null;
    }

    @Override
    public OAuthProvider.Session connect(final AccountCredentialType type,
                                         final String requestToken,
                                         final String requestTokenSecret) {
        final OAuthProvider provider = providers.get(type);
        return provider != null ? provider.connect(requestToken, requestTokenSecret) : null;
    }

    @Override
    public OAuthProvider.Session connect(final AccountCredentialType type) {
        final OAuthProvider provider = providers.get(type);
        return provider != null ? provider.connect() : null;
    }
}
