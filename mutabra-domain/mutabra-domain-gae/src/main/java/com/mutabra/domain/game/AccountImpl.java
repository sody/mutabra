package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;
import org.greatage.util.LocaleUtils;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.ACCOUNT)
public class AccountImpl extends BaseEntityImpl implements Account {

	@Indexed
	private String email;

	private String password;

	private String salt;

	@Indexed
	private String facebookUser;

	@Indexed
	private String twitterUser;

	@Indexed
	private String googleUser;

	@Indexed
	private String vkUser;

	private String pendingEmail;

	private String pendingPassword;

	private String pendingSalt;

	private String token;

	private String pendingToken;

	private Role role;

	@Unindexed
	private Date registered;

	private Date lastLogin;

	private String name;

	@Unindexed
	private String place;

	@Unindexed
	private String locale = "";

	@Transient
	private Locale localeValue;

	@Transient
	private TimeZone timeZone;

	private Key<HeroImpl> hero;

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}

	public String getPendingEmail() {
		return pendingEmail;
	}

	public void setPendingEmail(final String email) {
		this.pendingEmail = email;
	}

	public String getPendingPassword() {
		return pendingPassword;
	}

	public void setPendingPassword(final String password) {
		pendingPassword = password;
	}

	public String getPendingSalt() {
		return pendingSalt;
	}

	public void setPendingSalt(final String pendingSalt) {
		this.pendingSalt = pendingSalt;
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

	public String getVkUser() {
		return vkUser;
	}

	public void setVkUser(final String vkUser) {
		this.vkUser = vkUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
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

	public List<Hero> getHeroes() {
		return Keys.getChildren(Hero.class, HeroImpl.class, this);
	}
}
