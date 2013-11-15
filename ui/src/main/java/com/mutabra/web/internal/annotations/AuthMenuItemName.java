/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.annotations;

import com.mutabra.domain.Translatable;
import com.mutabra.web.pages.auth.RestoreAuth;
import com.mutabra.web.pages.auth.SignInAuth;
import com.mutabra.web.pages.auth.SignUpAuth;

/**
 * @author Ivan Khalopik
 */
public enum AuthMenuItemName implements Translatable {
    SIGN_IN(SignInAuth.class),
    SIGN_UP(SignUpAuth.class),
    RESTORE(RestoreAuth.class);

    private final Class<?> pageClass;
    private final String code;

    private AuthMenuItemName(final Class<?> pageClass) {
        this.pageClass = pageClass;
        code = name().replaceAll("_", "-").toLowerCase();
    }

    public Class<?> getPageClass() {
        return pageClass;
    }

    @Override
    public String getBasename() {
        return "menu";
    }

    @Override
    public String getCode() {
        return code;
    }
}
