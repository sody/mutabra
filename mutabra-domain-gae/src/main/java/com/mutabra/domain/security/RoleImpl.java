package com.mutabra.domain.security;

import com.google.appengine.api.datastore.Key;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Keys;
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

	@Persistent(mappedBy = "roles")
	private Set<Key> accounts = new HashSet<Key>();

	@Persistent
	private Set<Key> permissions = new HashSet<Key>();

	public Set<Account> getAccounts() {
		return Keys.getInstances(accounts, Account.class);
	}

	public Set<Permission> getPermissions() {
		return Keys.getInstances(permissions, Permission.class);
	}

	public void setPermissions(final Set<Permission> permissions) {
		this.permissions = Keys.getKeys(permissions);
	}

	public String getAuthority() {
		return ROLE_PREFIX + getCode().toUpperCase();
	}
}