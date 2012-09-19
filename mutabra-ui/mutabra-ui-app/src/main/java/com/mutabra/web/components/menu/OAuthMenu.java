package com.mutabra.web.components.menu;

import com.mutabra.security.OAuth;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.security.FacebookRealm;
import com.mutabra.web.internal.security.TwitterRealm;
import com.mutabra.web.pages.game.GameHome;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OAuthMenu extends AbstractComponent {

	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	Object facebookConnected(final OAuth.Session session) {
		getSubject().login(new FacebookRealm.Token(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "twitter")
	Object twitterConnected(final OAuth.Session session) {
		getSubject().login(new TwitterRealm.Token(session));
		return GameHome.class;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "google")
	Object googleConnected(final OAuth.Session session) {
		//TODO: implement this
		return GameHome.class;
	}
}
