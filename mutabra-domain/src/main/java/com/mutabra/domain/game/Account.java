package com.mutabra.domain.game;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Transient;
import com.mutabra.domain.BaseEntity;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity("accounts")
public class Account extends BaseEntity {

    @Indexed
    private String email;

    private String password;

    private String salt;

    private Role role;

    private Date registered;

    private Date lastLogin;


    @Indexed
    private String facebookUser;

    @Indexed
    private String twitterUser;

    @Indexed
    private String googleUser;

    @Indexed
    private String vkUser;


    private String token;

    private String pendingToken;

    private Long tokenExpired;

    private String pendingEmail;

    private String pendingPassword;

    private String pendingSalt;

    @Reference
    private Hero hero;

    private String name;

    private String place;

    private Locale locale;

    @Transient
    private TimeZone timeZone;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
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


    public String getFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(final String facebookUser) {
        this.facebookUser = facebookUser;
    }

    public String getTwitterUser() {
        return twitterUser;
    }

    public void setTwitterUser(final String twitterUser) {
        this.twitterUser = twitterUser;
    }

    public String getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(final String googleUser) {
        this.googleUser = googleUser;
    }

    public String getVkUser() {
        return vkUser;
    }

    public void setVkUser(final String vkUser) {
        this.vkUser = vkUser;
    }


    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getPendingToken() {
        return pendingToken;
    }

    public void setPendingToken(final String pendingToken) {
        this.pendingToken = pendingToken;
    }

    public Long getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(final Long tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public String getPendingEmail() {
        return pendingEmail;
    }

    public void setPendingEmail(final String pendingEmail) {
        this.pendingEmail = pendingEmail;
    }

    public String getPendingPassword() {
        return pendingPassword;
    }

    public void setPendingPassword(final String pendingPassword) {
        this.pendingPassword = pendingPassword;
    }

    public String getPendingSalt() {
        return pendingSalt;
    }

    public void setPendingSalt(final String pendingSalt) {
        this.pendingSalt = pendingSalt;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
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
}
