package com.mutabra.security;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface OAuth {

    String getAuthorizationUrl(String state, String scope);

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
