package com.mutabra.web.components.menu;

import com.mutabra.security.FacebookToken;
import com.mutabra.security.OAuth;
import com.mutabra.security.TwitterToken;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OAuthMenu extends AbstractComponent {

	@Inject
	private SecurityContext securityContext;

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	void facebookConnected(final OAuth.Session session) {
		securityContext.signIn(new FacebookToken(session));
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	void twitterConnected(final OAuth.Session session) {
		securityContext.signIn(new TwitterToken(session));
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "google")
	void googleConnected(final OAuth.Session session) {
		System.out.println();
	}
}
