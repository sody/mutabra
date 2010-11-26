package com.noname.web.components.security;

import com.noname.web.pages.Index;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.AuthenticationManager;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SignOutLink {

	@Inject
	private AuthenticationManager authenticationManager;

	Object onSignOut() {
		authenticationManager.signOut();
		return Index.class;
	}

}
