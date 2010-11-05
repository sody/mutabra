package com.noname.domain.security;

import com.noname.domain.CodedEntity;
import com.noname.domain.TranslationType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 */
@Entity
@Table(name = "ROLE")
public class Role extends CodedEntity {
	private static final String ROLE_PREFIX = "ROLE_";

	public Role() {
		super("role", TranslationType.STANDARD);
	}

	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts = new HashSet<Account>();

	@ManyToMany
	@JoinTable(name = "ROLE_PERMISSION",
			joinColumns = @JoinColumn(name = "ID_ROLE", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "ID_PERMISSION", nullable = false))
	private Set<Permission> permissions = new HashSet<Permission>();

	public Set<Account> getAccounts() {
		return accounts;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getAuthority() {
		return ROLE_PREFIX + getCode().toUpperCase();
	}
}