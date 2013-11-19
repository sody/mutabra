/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestGlobals;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 */
public class SecurityExceptionHandler implements RequestExceptionHandler {
    private final RequestExceptionHandler delegate;
    private final RequestGlobals globals;
    private final PageRenderLinkSource linkSource;
    private final String loginPage;

    public SecurityExceptionHandler(final RequestExceptionHandler delegate,
                                    final RequestGlobals globals,
                                    final PageRenderLinkSource linkSource,
                                    final String loginPage) {
        this.delegate = delegate;
        this.globals = globals;
        this.linkSource = linkSource;
        this.loginPage = loginPage;
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    public void handleRequestException(final Throwable exception) throws IOException {
        final Throwable rootException = getRootCause(exception);
        if (rootException instanceof AuthenticationException || rootException instanceof UnauthenticatedException) {
            final Link loginPageLink = linkSource.createPageRenderLink(loginPage);
            if (rootException instanceof AuthorizationException) {
                // save current request for later use after successful authentication
                WebUtils.saveRequest(globals.getHTTPServletRequest());

                loginPageLink.addParameter("not_authenticated", "y");
            } else {
                loginPageLink.addParameter("error", "y");
            }
            globals.getResponse().sendRedirect(loginPageLink);
        } else {
            delegate.handleRequestException(exception);
        }
    }

    private static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }
}
