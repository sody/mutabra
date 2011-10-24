package com.mutabra.web.components.menu;

import com.mutabra.security.FacebookToken;
import com.mutabra.security.GoogleService;
import com.mutabra.security.TwitterToken;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OAuthMenu extends AbstractComponent {

	@Inject
	private GoogleService googleService;

	@Inject
	private SecurityContext securityContext;

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	void facebookConnected(final String accessToken) {
		securityContext.signIn(new FacebookToken(accessToken));
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	void twitterConnected(final String token, final String secret, final String callbackUrl, final String scope) {
		securityContext.signIn(new TwitterToken(token, secret, callbackUrl, scope));
	}

	URL onConnectToGoogle() throws MalformedURLException {
		return new URL(googleService.getAuthorizationURL());
	}
}
