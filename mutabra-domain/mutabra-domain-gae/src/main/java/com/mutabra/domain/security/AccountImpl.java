package com.mutabra.domain.security;

import com.google.appengine.api.datastore.Key;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.Tables;
import com.mutabra.domain.player.Hero;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.ACCOUNT)
public class AccountImpl extends BaseEntityImpl implements Account {

	@Persistent
	private String email;

	@Persistent
	private String password;

	@Persistent
	private Date registered;

	@Persistent
	private Date lastLogin;

	@Persistent
	private String token;

	@Persistent
	private String name;

	@Persistent
	private String place;

	@Persistent
	private Locale locale;

	@Persistent
	private TimeZone timeZone;

	@Persistent
	private Date deletedAt;

	@Persistent
	private Set<Key> roles = new HashSet<Key>();

	@NotPersistent
	private Set<Hero> heroes = new HashSet<Hero>();

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

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
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

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(final Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Set<Role> getRoles() {
		return Keys.getInstances(roles, Role.class);
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = Keys.getKeys(roles);
	}

	public Set<Hero> getHeroes() {
		return heroes;
	}
}
