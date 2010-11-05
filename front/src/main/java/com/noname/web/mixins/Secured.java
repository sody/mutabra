package com.noname.web.mixins;

import ga.security.acl.AccessControlManager;
import ga.security.context.PermissionResolver;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

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
	private AccessControlManager accessControlManager;

	@Inject
	private PermissionResolver permissionResolver;

	boolean setupRender() {
		return permissionResolver.isGranted(securedObject, permission);
	}
}
