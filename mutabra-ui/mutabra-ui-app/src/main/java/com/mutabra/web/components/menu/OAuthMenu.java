package com.mutabra.web.components.menu;

import com.mutabra.security.BaseOAuthService;
import com.mutabra.security.FacebookService;
import com.mutabra.security.GoogleService;
import com.mutabra.security.TwitterService;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OAuthMenu extends AbstractComponent {

	@Inject
	private TwitterService twitterService;

	@Inject
	private FacebookService facebookService;

	@Inject
	private GoogleService googleService;

	URL onConnectToFacebook() throws MalformedURLException {
		return createAuthenticationURL(facebookService);
	}

	URL onConnectToGoogle() throws MalformedURLException {
		return createAuthenticationURL(googleService);
	}

	URL onConnectToTwitter() throws MalformedURLException {
		return createAuthenticationURL(twitterService);
	}

	private URL createAuthenticationURL(final BaseOAuthService authService) throws MalformedURLException {
		return new URL(authService.getAuthorizationURL());
	}
}
