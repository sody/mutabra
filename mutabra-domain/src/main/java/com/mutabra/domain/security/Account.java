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

	public String getEmail();

	public void setEmail(final String email);

	public String getPassword();

	public void setPassword(final String password);

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
}
