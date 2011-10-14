package com.mutabra.domain.security;

import com.google.appengine.api.datastore.Key;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.db.Tables;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable(table = Tables.PERMISSION)
public class PermissionImpl extends CodedEntityImpl implements Permission {

	@Persistent(mappedBy = "permissions")
	private Set<Key> roles = new HashSet<Key>();

	public PermissionImpl() {
		super(Tables.PERMISSION, TranslationType.STANDARD);
	}

	public Set<Role> getRoles() {
		return Keys.getInstances(roles, Role.class);
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = Keys.getKeys(roles);
	}

	public String getAuthority() {
		return PERMISSION_PREFIX + getCode().toUpperCase();
	}
}