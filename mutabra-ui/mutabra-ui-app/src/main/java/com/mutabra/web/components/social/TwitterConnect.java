package com.mutabra.web.components.social;

import com.mutabra.security.OAuth;
import com.mutabra.web.base.components.AbstractOAuthConnect;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterConnect extends AbstractOAuthConnect {

    @InjectService("twitterService")
    private OAuth twitterService;

    @Override
    protected OAuth getOAuth() {
        return twitterService;
    }

    @OnEvent(CONNECTED_EVENT)
    Object connected(
            @RequestParameter(value = "oauth_token", allowBlank = true) String token,
            @RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
            @RequestParameter(value = "denied", allowBlank = true) final String denied) {

        return doConnected(token, verifier, denied);
    }
}
