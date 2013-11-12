/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.annotations;

import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 */
public enum AuthMenuItemName implements Translatable {
    SIGN_IN,
    SIGN_UP,
    RESTORE;

    private final String code;

    private AuthMenuItemName() {
        code = name().replaceAll("_", "-").toLowerCase();
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
