/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.services;

import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.security.OAuthProvider;
import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;

/**
 * @author Ivan Khalopik
 */
@UsesMappedConfiguration(key = AccountCredentialType.class, value = OAuthProvider.class)
public interface OAuthSource {

    String getAuthorizationUrl(AccountCredentialType type, String scope);

    OAuthProvider.Session connect(AccountCredentialType type, String requestToken, String requestTokenSecret);

    OAuthProvider.Session connect(AccountCredentialType type);
}
