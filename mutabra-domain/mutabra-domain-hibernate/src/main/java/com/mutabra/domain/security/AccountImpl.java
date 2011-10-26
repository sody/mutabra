package com.mutabra.domain.security;

import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = Tables.ACCOUNT)
public class AccountImpl extends BaseEntityImpl implements Account {

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "PENDING_PASSWORD", nullable = true)
	private String pendingPassword;

	@Column(name = "FACEBOOK_USER", nullable = true)
	private String facebookUser;

	@Column(name = "TWITTER_USER", nullable = true)
	private String twitterUser;

	@Column(name = "GOOGLE_USER", nullable = true)
	private String googleUser;

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

	public String getPendingPassword() {
		return pendingPassword;
	}

	public void setPendingPassword(final String password) {
		this.pendingPassword = password;
	}

	public String getFacebookUser() {
		return facebookUser;
	}

	public void setFacebookUser(final String user) {
		this.facebookUser = user;
	}

	public String getTwitterUser() {
		return twitterUser;
	}

	public void setTwitterUser(final String user) {
		this.twitterUser = user;
	}

	public String getGoogleUser() {
		return googleUser;
	}

	public void setGoogleUser(final String user) {
		this.googleUser = user;
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

	public String getDisplayName() {
		return getEmail();
	}
}
