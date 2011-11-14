package com.mutabra.domain.security;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.player.Hero;

import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Account extends BaseEntity {

	String getEmail();

	void setEmail(String email);

	String getPendingEmail();

	void setPendingEmail(String email);

	String getPassword();

	void setPassword(String password);

	String getPendingPassword();

	void setPendingPassword(String password);

	String getToken();

	void setToken(String token);

	String getPendingToken();

	void setPendingToken(String token);

	String getFacebookUser();

	void setFacebookUser(String user);

	String getTwitterUser();

	void setTwitterUser(String user);

	String getGoogleUser();

	void setGoogleUser(String user);

	Date getRegistered();

	void setRegistered(Date registered);

	Date getLastLogin();

	void setLastLogin(Date lastLogin);

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

	Set<Role> getRoles();

	void setRoles(Set<Role> roles);

	Set<Hero> getHeroes();
}
