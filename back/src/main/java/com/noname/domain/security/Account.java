package com.noname.domain.security;

import com.noname.domain.BaseEntity;
import com.noname.domain.player.Hero;
import org.greatage.util.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Khalopik
 */
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity {
	public static final String EMAIL_PROPERTY = "email";

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

	@ManyToMany
	@JoinTable(name = "ACCOUNT_ROLE",
			joinColumns = @JoinColumn(name = "ID_ACCOUNT", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "ID_ROLE", nullable = false))
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(mappedBy = "account")
	private Set<Hero> heroes = new HashSet<Hero>();

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getName() {
		return email;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Hero> getHeroes() {
		return heroes;
	}

	public List<String> createAuthorities() {
		final List<String> authorities = CollectionUtils.newList();
		authorities.add(email);
		for (Role role : getRoles()) {
			authorities.add(role.getAuthority());
			for (Permission permission : role.getPermissions()) {
				authorities.add(permission.getAuthority());
			}
		}
		return authorities;
	}
}
