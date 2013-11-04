package com.mutabra.domain.game;

import org.mongodb.morphia.annotations.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class AccountPendingToken {
    private String token;
    private String secondaryToken;
    private Long expiredAt;

    private String email;
    private String secret;
    private String salt;

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getSecondaryToken() {
        return secondaryToken;
    }

    public void setSecondaryToken(final String secondaryToken) {
        this.secondaryToken = secondaryToken;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(final Long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public boolean isExpired() {
        return expiredAt == null || expiredAt < System.currentTimeMillis();
    }
}
