package com.mutabra.web.internal.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderRequestParameters;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityFilter implements ComponentRequestFilter {
	public static final String SHIRO_REQUIRES_AUTHENTICATION_META = "shiro.requires-authentication";
	public static final String SHIRO_REQUIRES_USER_META = "shiro.requires-user";
	public static final String SHIRO_REQUIRES_GUEST_META = "shiro.requires-guest";
	public static final String SHIRO_REQUIRES_ROLES_META = "shiro.requires-roles";
	public static final String SHIRO_REQUIRES_ROLES_LOGICAL_META = "shiro.requires-roles-logical";
	public static final String SHIRO_REQUIRES_PERMISSIONS_META = "shiro.requires-permissions";
	public static final String SHIRO_REQUIRES_PERMISSIONS_LOGICAL_META = "shiro.requires-permissions-logical";

	private final MetaDataLocator locator;

	public SecurityFilter(final MetaDataLocator locator) {
		this.locator = locator;
	}

	public void handleComponentEvent(final ComponentEventRequestParameters parameters, final ComponentRequestHandler handler) throws IOException {
		//todo: implement this
		handler.handleComponentEvent(parameters);
	}

	public void handlePageRender(final PageRenderRequestParameters parameters, final ComponentRequestHandler handler) throws IOException {
		final String pageName = parameters.getLogicalPageName();
		final boolean authenticated = locator.findMeta(SHIRO_REQUIRES_AUTHENTICATION_META, pageName, Boolean.class);
		if (authenticated) {
			checkAuthenticated();
		}

		final boolean user = locator.findMeta(SHIRO_REQUIRES_USER_META, pageName, Boolean.class);
		if (user) {
			checkUser();
		}

		final boolean guest = locator.findMeta(SHIRO_REQUIRES_GUEST_META, pageName, Boolean.class);
		if (guest) {
			checkGuest();
		}

		final String[] roles = locator.findMeta(SHIRO_REQUIRES_ROLES_META, pageName, String[].class);
		if (roles != null) {
			final boolean logical = locator.findMeta(SHIRO_REQUIRES_ROLES_LOGICAL_META, pageName, Boolean.class);
			checkRoles(logical ? Logical.AND : Logical.OR, roles);
		}

		final String[] permissions = locator.findMeta(SHIRO_REQUIRES_PERMISSIONS_META, pageName, String[].class);
		if (permissions != null) {
			final boolean logical = locator.findMeta(SHIRO_REQUIRES_PERMISSIONS_LOGICAL_META, pageName, Boolean.class);
			checkPermissions(logical ? Logical.AND : Logical.OR, permissions);
		}

		handler.handlePageRender(parameters);
	}

	private void checkAuthenticated() throws AuthorizationException {
		if (!getSubject().isAuthenticated()) {
			throw new UnauthenticatedException("The current Subject is not authenticated. Access denied.");
		}
	}

	private void checkUser() throws AuthorizationException {
		if (getSubject().getPrincipal() == null) {
			throw new UnauthenticatedException("Attempting to perform a user-only operation. The current Subject is " +
					"not a user (they haven't been authenticated or remembered from a previous login). " +
					"Access denied.");
		}
	}

	private void checkGuest() throws AuthorizationException {
		if (getSubject().getPrincipal() != null) {
			throw new UnauthenticatedException("Attempting to perform a guest-only operation. The current Subject is " +
					"not a guest (they have been authenticated or remembered from a previous login). Access " +
					"denied.");
		}
	}

	private void checkRoles(final Logical logical, final String... roles) throws AuthorizationException {
		if (roles.length == 1) {
			getSubject().checkRole(roles[0]);
			return;
		}
		if (logical == Logical.AND) {
			getSubject().checkRoles(roles);
			return;
		}
		if (logical == Logical.OR) {
			// Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
			boolean hasAtLeastOneRole = false;
			for (String role : roles) {
				if (getSubject().hasRole(role)) {
					hasAtLeastOneRole = true;
				}
			}
			// Cause the exception if none of the role match, note that the exception message will be a bit misleading
			if (!hasAtLeastOneRole) {
				getSubject().checkRole(roles[0]);
			}
		}
	}

	private void checkPermissions(final Logical logical, final String... permissions) throws AuthorizationException {
		if (permissions.length == 1) {
			getSubject().checkPermission(permissions[0]);
			return;
		}
		if (logical == Logical.AND) {
			getSubject().checkPermissions(permissions);
			return;
		}
		if (logical == Logical.OR) {
			// Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
			boolean hasAtLeastOnePermission = false;
			for (String permission : permissions) {
				if (getSubject().isPermitted(permission)) {
					hasAtLeastOnePermission = true;
				}
			}
			// Cause the exception if none of the role match, note that the exception message will be a bit misleading
			if (!hasAtLeastOnePermission) {
				getSubject().checkPermission(permissions[0]);
			}
		}
	}

	private Subject getSubject() {
		return SecurityUtils.getSubject();
	}
}
