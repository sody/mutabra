/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.security;

import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.web.services.OAuthSource;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractComponentEventLink;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ivan Khalopik
 */
public class OAuthConnect extends AbstractComponentEventLink {
    protected static final String CONNECT_EVENT = "connect";

    @Parameter(required = true, allowNull = false)
    private AccountCredentialType credentialType;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String scope;

    @Inject
    private OAuthSource oauthSource;

    @Inject
    private ComponentResources resources;

    @Inject
    private Logger logger;

    protected String getScope() {
        return scope;
    }

    @Override
    protected Link createLink(final Object[] eventContext) {
        return resources.createEventLink(CONNECT_EVENT);
    }

    @OnEvent(CONNECT_EVENT)
    protected URL connect() throws MalformedURLException {
        final String authorizationUrl = oauthSource.getAuthorizationUrl(credentialType, scope);
        logger.debug("Redirecting to OAuth authorization URL: '{}'", authorizationUrl);
        return new URL(authorizationUrl);
    }
}
