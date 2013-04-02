package com.mutabra.web.base.components;

import com.mutabra.security.OAuth;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractComponentEventLink;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractOAuthConnect extends AbstractComponentEventLink {
    protected static final String CONNECT_EVENT = "connect";
    protected static final String CONNECTED_EVENT = "connected";

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String scope;

    @Inject
    private ComponentResources resources;

    @Inject
    private Logger logger;

    protected String getRedirectUri() {
        return resources.createEventLink(CONNECTED_EVENT).toRedirectURI();
    }

    protected String getScope() {
        return scope;
    }

    @Override
    protected Link createLink(final Object[] eventContext) {
        return resources.createEventLink(CONNECT_EVENT);
    }

    @OnEvent(CONNECT_EVENT)
    protected URL connect() throws MalformedURLException {
        final String authorizationUrl = getOAuth().getAuthorizationUrl(getRedirectUri(), scope);
        logger.debug("Redirecting to OAuth authorization URL: '{}'", authorizationUrl);
        return new URL(authorizationUrl);
    }

    protected abstract OAuth getOAuth();

    protected OAuth.Session startSession(final String token, final String secret) {
        return getOAuth().connect(token, secret);
    }

    protected Object doConnected(final String token, final String secret, final String error) {
        logger.debug("Reply from OAuth authorization screen:\n" +
                "\ttoken : {}\n" +
                "\tsecret: {}\n" +
                "\terror : {}", new Object[]{token, secret, error});
        final CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
        if (secret != null) {
            try {
                final OAuth.Session session = startSession(token, secret);
                final Object[] context = {session};
                final boolean handled = resources.triggerEvent(EventConstants.SUCCESS, context, callback);

                if (handled) {
                    return callback.getResult();
                }
                return null;
            } catch (Exception e) {
                logger.error("Can not connect to OAuth provider", e);
            }
        }

        final Object[] context = {error};
        final boolean handled = resources.triggerEvent(EventConstants.FAILURE, context, callback);

        if (handled) {
            return callback.getResult();
        }
        return null;
    }
}
