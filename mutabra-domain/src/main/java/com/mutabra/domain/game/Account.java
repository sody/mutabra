package com.mutabra.domain.game;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Transient;
import com.mutabra.domain.BaseEntity;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "accounts", noClassnameStored = true)
public class Account extends BaseEntity {

    private String name;
    private Locale locale;

    @Transient
    private TimeZone timeZone;

    private String location;
    private AccountHero hero;

    private Role role;
    private Date registered;
    private Date lastLogin;

    @Embedded
    private Set<AccountCredential> credentials = new HashSet<AccountCredential>();

    private AccountPendingToken pendingToken;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public AccountHero getHero() {
        return hero;
    }

    public void setHero(final AccountHero hero) {
        this.hero = hero;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(final Date registered) {
        this.registered = registered;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(final Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<AccountCredential> getCredentials() {
        return credentials;
    }

    public AccountPendingToken getPendingToken() {
        return pendingToken;
    }

    public void setPendingToken(final AccountPendingToken pendingToken) {
        this.pendingToken = pendingToken;
    }

    public AccountCredential getCredentials(final AccountCredentialType type) {
        for (AccountCredential credential : credentials) {
            if (credential.getType() == type) {
                return credential;
            }
        }
        return null;
    }
}
