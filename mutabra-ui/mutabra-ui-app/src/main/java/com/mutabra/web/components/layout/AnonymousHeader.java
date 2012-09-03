package com.mutabra.web.components.layout;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.game.GameHome;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnonymousHeader extends AbstractComponent {

	@Property
	private String email;

	@Property
	private String password;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		getSubject().login(new UsernamePasswordToken(email, password));
		return GameHome.class;
	}
}
