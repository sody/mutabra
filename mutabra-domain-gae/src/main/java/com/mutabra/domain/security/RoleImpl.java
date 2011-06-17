package com.mutabra.domain.security;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable
public class RoleImpl extends CodedEntityImpl implements Role {
	public RoleImpl() {
		super("ROLE", TranslationType.STANDARD);
	}

	@Persistent
	private Set<Account> accounts = new HashSet<Account>();

	@Persistent
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