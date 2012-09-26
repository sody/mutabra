package com.mutabra.web.services;

import org.apache.shiro.crypto.hash.Hash;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface PasswordGenerator {

	String generateSecret();

	Hash generateHash(String secret);

	Hash generateHash();
}
