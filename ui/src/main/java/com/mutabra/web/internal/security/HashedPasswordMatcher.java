/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.security;

import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HashedPasswordMatcher extends CodecSupport implements PasswordGenerator, CredentialsMatcher {
    private final RandomNumberGenerator generator = new SecureRandomNumberGenerator();

    private final HashService hashService;
    private final long expirationTime;

    private boolean storedCredentialsHexEncoded;

    public HashedPasswordMatcher(final HashService hashService, final long expirationTime) {
        this.hashService = hashService;
        this.expirationTime = expirationTime;
    }

    public boolean isStoredCredentialsHexEncoded() {
        return storedCredentialsHexEncoded;
    }

    public void setStoredCredentialsHexEncoded(final boolean storedCredentialsHexEncoded) {
        this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
    }

    public String generateSecret() {
        return generator.nextBytes().toBase64().replaceAll("=", "");
    }

    public Hash generateHash(final String secret) {
        return hashService.computeHash(new HashRequest.Builder().setSource(secret).build());
    }

    public Hash generateHash() {
        return hashService.computeHash(new HashRequest.Builder().setSource(generator.nextBytes()).build());
    }

    public long generateExpirationTime() {
        return System.currentTimeMillis() + expirationTime;
    }

    public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
        final Hash tokenHash = getHash(token, info);
        final Hash accountHash = getHash(info);

        // it makes possible to have empty passwords
        if (accountHash == null || accountHash.isEmpty()) {
            return tokenHash == null || tokenHash.isEmpty();
        } else {
            if (tokenHash == null || tokenHash.isEmpty()) {
                return false;
            }
        }

        return accountHash.equals(tokenHash);
    }

    protected Hash getHash(final AuthenticationInfo info) {
        final Object credentials = info != null ? info.getCredentials() : null;
        // it makes possible to have empty passwords
        if (credentials == null) {
            return null;
        }

        byte[] bytes = toBytes(credentials);
        // text credentials should be decoded
        if (credentials instanceof String || credentials instanceof char[]) {
            if (isStoredCredentialsHexEncoded()) {
                bytes = Hex.decode(bytes);
            } else {
                bytes = Base64.decode(bytes);
            }
        }
        // hash has already been calculated
        // so we don't need to calculate it with HashService
        final SimpleHash hash = new SimpleHash(null);
        hash.setBytes(bytes);
        return hash;
    }

    protected Hash getHash(final AuthenticationToken token, final AuthenticationInfo info) {
        final Object credentials = token != null ? token.getCredentials() : null;
        final Object salt = info instanceof SaltedAuthenticationInfo ?
                ((SaltedAuthenticationInfo) info).getCredentialsSalt() :
                null;
        // it makes possible to have empty passwords
        if (credentials == null) {
            return null;
        }

        // generate hash using hash service
        return hashService.computeHash(new HashRequest.Builder()
                .setSource(credentials)
                .setSalt(salt)
                .build());
    }
}
