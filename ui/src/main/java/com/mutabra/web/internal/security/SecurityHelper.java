/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * @author Ivan Khalopik
 */
public abstract class SecurityHelper {

    public boolean isAuthenticated() {
        return getSubject().isAuthenticated();
    }

    public void checkAuthenticated() throws AuthorizationException {
        if (!getSubject().isAuthenticated()) {
            throw new UnauthenticatedException("The current Subject is not authenticated. Access denied.");
        }
    }

    public boolean isUser() {
        return getSubject().getPrincipal() != null;
    }

    public void checkUser() throws AuthorizationException {
        if (getSubject().getPrincipal() == null) {
            throw new UnauthenticatedException("Attempting to perform a user-only operation. The current Subject is " +
                    "not a user (they haven't been authenticated or remembered from a previous login). " +
                    "Access denied.");
        }
    }

    public boolean isGuest() {
        return getSubject().getPrincipal() == null;
    }

    public void checkGuest() throws AuthorizationException {
        if (getSubject().getPrincipal() != null) {
            throw new UnauthenticatedException("Attempting to perform a guest-only operation. The current Subject is " +
                    "not a guest (they have been authenticated or remembered from a previous login). Access " +
                    "denied.");
        }
    }

    public boolean hasRoles(final Logical logical, final String... roles) {
        if (roles.length == 1) {
            return getSubject().hasRole(roles[0]);
        }

        if (logical == Logical.AND) {
            return getSubject().hasAllRoles(Arrays.asList(roles));
        }
        if (logical == Logical.OR) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
            for (String role : roles) {
                if (getSubject().hasRole(role)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkRoles(final Logical logical, final String... roles) throws AuthorizationException {
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

    public boolean isPermitted(final Logical logical, final String... permissions) {
        if (permissions.length == 1) {
            return getSubject().isPermitted(permissions[0]);
        }

        if (logical == Logical.AND) {
            return getSubject().isPermittedAll(permissions);
        }
        if (logical == Logical.OR) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
            for (String permission : permissions) {
                if (getSubject().isPermitted(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkPermissions(final Logical logical, final String... permissions) throws AuthorizationException {
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

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
