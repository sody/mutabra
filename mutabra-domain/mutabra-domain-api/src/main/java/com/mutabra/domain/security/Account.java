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

	String EMAIL_PROPERTY = "email";

	String PASSWORD_PROPERTY = "password";

	String TOKEN_PROPERTY = "token";

	String FACEBOOK_USER_PROPERTY = "facebookUser";

	String TWITTER_USER_PROPERTY = "twitterUser";

	String GOOGLE_USER_PROPERTY = "googleUser";

	public String getEmail();

	public void setEmail(final String email);

	public String getPassword();

	public void setPassword(final String password);

	public String getPendingPassword();

	public void setPendingPassword(final String password);

	public String getFacebookUser();

	public void setFacebookUser(String user);

	public String getTwitterUser();

	public void setTwitterUser(String user);

	public String getGoogleUser();

	public void setGoogleUser(String user);

	public Date getRegistered();

	public void setRegistered(final Date registered);

	public Date getLastLogin();

	public void setLastLogin(final Date lastLogin);

	public String getToken();

	public void setToken(final String token);

	public String getName();

	public void setName(final String name);

	public String getPlace();

	public void setPlace(final String place);

	public Locale getLocale();

	public void setLocale(final Locale locale);

	public TimeZone getTimeZone();

	public void setTimeZone(final TimeZone timeZone);

	public Date getDeletedAt();

	public void setDeletedAt(final Date deletedAt);

	public Set<Role> getRoles();

	public void setRoles(final Set<Role> roles);

	public Set<Hero> getHeroes();

	public String getDisplayName();
}
