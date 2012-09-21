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

	String getEmail();

	void setEmail(String email);

	String getPassword();

	void setPassword(String password);

	String getSalt();

	void setSalt(final String salt);

	String getToken();

	void setToken(String token);

	String getPendingEmail();

	void setPendingEmail(String email);

	String getPendingPassword();

	void setPendingPassword(String password);

	String getPendingSalt();

	void setPendingSalt(String salt);

	String getPendingToken();

	void setPendingToken(String token);

	String getFacebookUser();

	void setFacebookUser(String user);

	String getTwitterUser();

	void setTwitterUser(String user);

	String getGoogleUser();

	void setGoogleUser(String user);

	String getVkUser();

	void setVkUser(String user);

	Role getRole();

	void setRole(Role role);

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

	List<Hero> getHeroes();
}
