/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.annotations;

import com.mutabra.web.pages.auth.RestoreAuth;
import com.mutabra.web.pages.auth.SignInAuth;
import com.mutabra.web.pages.auth.SignUpAuth;
import com.mutabra.web.services.MenuModelSource;

/**
 * @author Ivan Khalopik
 */
public enum AuthMenuItem implements MenuModelSource.Item {
    SIGN_IN(SignInAuth.class),
    SIGN_UP(SignUpAuth.class),
    RESTORE(RestoreAuth.class);

    private final Class<?> pageClass;

    private AuthMenuItem(final Class<?> pageClass) {
        this.pageClass = pageClass;
    }

    public Class<?> getPageClass() {
        return pageClass;
    }

    @Override
    public Class<? extends MenuModelSource.Item> getChildMenuClass() {
        return null;
    }
}
