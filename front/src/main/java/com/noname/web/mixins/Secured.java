package com.noname.web.mixins;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityChecker;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Secured {

	@Parameter(required = true)
	private Object securedObject;

	@Parameter(required = true)
	private String permission;

	@Inject
	private SecurityChecker securityChecker;

	boolean setupRender() {
		try {
			securityChecker.checkPermission(securedObject, permission);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
