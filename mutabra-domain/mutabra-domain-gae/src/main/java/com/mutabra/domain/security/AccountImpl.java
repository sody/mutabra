package com.mutabra.domain.security;

import com.google.appengine.api.datastore.Key;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroImpl;
import org.greatage.util.LocaleUtils;

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
	private String pendingEmail;

	@Persistent
	private String password;

	@Persistent
	private String pendingPassword;

	@Persistent
	private String token;

	@Persistent
	private String pendingToken;

	@Persistent
	private String facebookUser;

	@Persistent
	private String twitterUser;

	@Persistent
	private String googleUser;

	@Persistent
	private Date registered;

	@Persistent
	private Date lastLogin;

	@Persistent
	private String name;

	@Persistent
	private String place;

	@Persistent
	private String locale = "";

	@NotPersistent
	private Locale localeValue;

	@Persistent
	private TimeZone timeZone;

	@NotPersistent
	private HeroImpl hero;

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

	public String getPendingEmail() {
		return pendingEmail;
	}

	public void setPendingEmail(final String email) {
		this.pendingEmail = email;
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
		pendingPassword = password;
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

	public void setPendingToken(final String token) {
		this.pendingToken = token;
	}

	public String getFacebookUser() {
		return facebookUser;
	}

	public void setFacebookUser(final String user) {
		facebookUser = user;
	}

	public String getTwitterUser() {
		return twitterUser;
	}

	public void setTwitterUser(final String user) {
		twitterUser = user;
	}

	public String getGoogleUser() {
		return googleUser;
	}

	public void setGoogleUser(final String user) {
		googleUser = user;
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
		if (localeValue == null) {
			localeValue = LocaleUtils.parseLocale(locale);
		}
		return localeValue;
	}

	public void setLocale(final Locale locale) {
		localeValue = locale;
		this.locale = locale != null ? locale.toString() : null;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(final TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(final Hero hero) {
		this.hero = (HeroImpl) hero;
	}

	public Set<Role> getRoles() {
		return Keys.getInstances(roles, Role.class, RoleImpl.class);
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = Keys.getKeys(roles);
	}

	public Set<Hero> getHeroes() {
		return heroes;
	}
}
