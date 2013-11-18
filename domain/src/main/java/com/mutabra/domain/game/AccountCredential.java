/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.game;

import org.mongodb.morphia.annotations.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class AccountCredential {
    private AccountCredentialType type;
    private String key;
    private String secret;
    private String salt;

    public AccountCredentialType getType() {
        return type;
    }

    public void setType(AccountCredentialType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
