package com.mutabra.domain.security;

import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroImpl;

import javax.persistence.*;
import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "ACCOUNT")
public class AccountImpl extends BaseEntityImpl implements Account {

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "REGISTRATION_DATE", nullable = true, updatable = false)
	private Date registered;

	@Column(name = "LAST_LOGIN_DATE", nullable = true, insertable = false)
	private Date lastLogin;

	@Column(name = "TOKEN", nullable = true)
	private String token;

	@Column(name = "NAME", nullable = true, insertable = false)
	private String name;

	@Column(name = "PLACE", nullable = true, insertable = false)
	private String place;

	@Column(name = "LOCALE", nullable = true, insertable = false)
	private Locale locale;

	@Column(name = "TIMEZONE", nullable = true, insertable = false)
	private TimeZone timeZone;

	@Column(name = "DELETED_AT", nullable = true, insertable = false)
	private Date deletedAt;

	@ManyToMany(targetEntity = RoleImpl.class)
	@JoinTable(name = "ACCOUNT_ROLE",
			joinColumns = @JoinColumn(name = "ID_ACCOUNT", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "ID_ROLE", nullable = false))
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(mappedBy = "account", targetEntity = HeroImpl.class)
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
		return roles;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Hero> getHeroes() {
		return heroes;
	}
}
