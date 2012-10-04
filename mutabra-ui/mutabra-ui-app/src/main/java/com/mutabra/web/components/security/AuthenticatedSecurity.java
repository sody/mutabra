package com.mutabra.web.components.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.corelib.base.AbstractConditional;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthenticatedSecurity extends AbstractConditional {

	@Override
	protected boolean test() {
		return getSubject() != null && getSubject().isAuthenticated();
	}

	private Subject getSubject() {
		return SecurityUtils.getSubject();
	}
}
