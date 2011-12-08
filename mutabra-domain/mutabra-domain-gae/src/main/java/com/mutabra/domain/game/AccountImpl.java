package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.security.Role;
import org.greatage.util.LocaleUtils;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.ACCOUNT)
public class AccountImpl extends BaseEntityImpl implements Account {

	private String email;

	private String pendingEmail;

	private String password;

	private String pendingPassword;

	private String token;

	private String pendingToken;

	@Indexed
	private String facebookUser;

	@Indexed
	private String twitterUser;

	@Indexed
	private String googleUser;

	private Date registered;

	private Date lastLogin;

	private String name;

	private String place;

	private String locale = "";

	@Transient
	private Locale localeValue;

	@Transient
	private TimeZone timeZone;

	private Key<HeroImpl> hero;

	private Set<Role> roles = new HashSet<Role>();

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
		return Keys.getInstance(hero);
	}

	public void setHero(final Hero hero) {
		this.hero = Keys.getKey(hero);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	public List<Hero> getHeroes() {
		return Keys.getChildren(Hero.class, HeroImpl.class, this);
	}
}
