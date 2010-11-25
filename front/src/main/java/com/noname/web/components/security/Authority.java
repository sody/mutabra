package com.noname.web.components.security;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityChecker;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Authority {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String authority;

	@Inject
	private SecurityChecker securityChecker;

	boolean setupRender() {
		try {
			securityChecker.check(null, authority);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
