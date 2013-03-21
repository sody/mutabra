package com.mutabra.web.internal.security;

import org.apache.shiro.authz.annotation.Logical;
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
public class SecurityFilter extends SecurityHelper implements ComponentRequestFilter {
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

    public void handleComponentEvent(final ComponentEventRequestParameters parameters,
                                     final ComponentRequestHandler handler) throws IOException {
        checkPage(parameters.getActivePageName());

        handler.handleComponentEvent(parameters);
    }

    public void handlePageRender(final PageRenderRequestParameters parameters,
                                 final ComponentRequestHandler handler) throws IOException {
        checkPage(parameters.getLogicalPageName());

        handler.handlePageRender(parameters);
    }

    private void checkPage(final String pageName) {
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
    }
}
