package com.mutabra.web.components.layout;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.PasswordAuthenticationToken;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AnonymousHeader extends AbstractComponent {

	@Property
	private String email;

	@Property
	private String password;

	@Inject
	private SecurityContext securityContext;

	@OnEvent(value = EventConstants.SUCCESS, component = "signIn")
	Object signIn() {
		securityContext.signIn(new PasswordAuthenticationToken(email, password));
		return getResources().getPageName();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signUp")
	Object signUp() {
		//todo: make it work
		return getResources().getPageName();
	}
}
