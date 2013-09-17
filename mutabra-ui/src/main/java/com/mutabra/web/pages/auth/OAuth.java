package com.mutabra.web.pages.auth;

import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.web.base.pages.AbstractAuthPage;
import com.mutabra.web.internal.security.OAuthRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Ivan Khalopik
 */
public class OAuth extends AbstractAuthPage {

    @Inject
    private Request request;

    @Inject
    private Response response;

    @Inject
    private HttpServletRequest servletRequest;

    @OnEvent(value = EventConstants.ACTIVATE)
    Object authenticate(final AccountCredentialType type) throws IOException {
        if (type != null) {
            switch (type) {
                case TWITTER:
                    return oauth(type, "oauth_token", "oauth_token", "denied");
                case FACEBOOK:
                case GOOGLE:
                case VK:
                    return oauth2(type, "code", "error_description");
            }
        }

        // if credentials type is not supported
        // then go to security page
        throw new AuthenticationException("OAuth provider is not supported.");
    }

    private Object oauth2(final AccountCredentialType type,
                          final String codeParameter,
                          final String errorParameter) throws IOException {
        return oauth(type, codeParameter, codeParameter, errorParameter);
    }

    private Object oauth(final AccountCredentialType type,
                         final String tokenParameter,
                         final String secretParameter,
                         final String errorParameter) throws IOException {
        final String token = request.getParameter(tokenParameter);
        final String secret = request.getParameter(secretParameter);
        final String error = request.getParameter(errorParameter);
        getSubject().login(new OAuthRealm.Token(type, token, secret, error));

        return authenticated();
    }
}
