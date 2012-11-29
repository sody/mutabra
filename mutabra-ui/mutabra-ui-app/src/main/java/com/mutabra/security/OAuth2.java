package com.mutabra.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface OAuth2 extends OAuth {

    Session connect(String code);
}
