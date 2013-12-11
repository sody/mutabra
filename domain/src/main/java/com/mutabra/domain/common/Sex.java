/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.common;

import com.mutabra.domain.CodeUtils;
import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 */
public enum Sex implements Translatable {
    MALE,
    FEMALE;

    private static final String BASENAME = "sex";
    private final String code;

    private Sex() {
        code = CodeUtils.generateCode(this);
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
