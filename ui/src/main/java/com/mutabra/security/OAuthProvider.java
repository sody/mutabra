/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.security;

import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public interface OAuthProvider {

    String getAuthorizationUrl(String scope);

    Session connect(String requestToken, String requestTokenSecret);

    Session connect();

    interface Session {
        String ID = "id";
        String EMAIL = "email";
        String NAME = "name";
        String LOCALE = "locale";

        Map<String, Object> getProfile();

        Map<String, Object> getProfile(String id);
    }
}
