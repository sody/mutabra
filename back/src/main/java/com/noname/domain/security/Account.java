package com.noname.domain.security;

import com.noname.domain.BaseEntity;
import ga.util.CollectionUtils;

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

	@ManyToMany
	@JoinTable(name = "ACCOUNT_ROLE",
			joinColumns = @JoinColumn(name = "ID_ACCOUNT", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "ID_ROLE", nullable = false))
	private Set<Role> roles = new HashSet<Role>();


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isExpired() {
		return false;
	}

	public boolean isLocked() {
		return false;
	}

	public boolean isPasswordExpired() {
		return false;
	}

	public String getAuthority() {
		return null;
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
