/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum AccountCredentialType {
    EMAIL,
    TWITTER,
    FACEBOOK,
    GOOGLE,
    VK;

    private static final String BASENAME = "credential-type";
    private final String code;

    private AccountCredentialType() {
        code = name().replaceAll("([A-Z])", "-$1").toLowerCase();
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
