package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Account extends BaseEntity {

    /* Security Information */
    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    String getSalt();

    void setSalt(final String salt);

    Role getRole();

    void setRole(Role role);

    Date getRegistered();

    void setRegistered(Date registered);

    Date getLastLogin();

    void setLastLogin(Date lastLogin);


    /* Social Network Profiles */
    String getFacebookUser();

    void setFacebookUser(String user);

    String getTwitterUser();

    void setTwitterUser(String user);

    String getGoogleUser();

    void setGoogleUser(String user);

    String getVkUser();

    void setVkUser(String user);


    /* Pending Changes */
    String getToken();

    void setToken(String token);

    String getPendingToken();

    void setPendingToken(String token);

    Long getTokenExpired();

    void setTokenExpired(Long tokenExpired);

    String getPendingEmail();

    void setPendingEmail(String email);

    String getPendingPassword();

    void setPendingPassword(String password);

    String getPendingSalt();

    void setPendingSalt(String salt);


    /* General Information */
    String getName();

    void setName(String name);

    String getPlace();

    void setPlace(String place);

    Locale getLocale();

    void setLocale(Locale locale);

    TimeZone getTimeZone();

    void setTimeZone(TimeZone timeZone);

    Hero getHero();

    void setHero(Hero hero);

    List<Hero> getHeroes();
}
