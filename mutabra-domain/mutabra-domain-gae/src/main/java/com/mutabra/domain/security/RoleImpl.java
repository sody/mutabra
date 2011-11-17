package com.mutabra.domain.security;

import com.googlecode.objectify.Key;
import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.ROLE)
public class RoleImpl extends CodedEntityImpl implements Role {
	private Set<Key<PermissionImpl>> permissions = new HashSet<Key<PermissionImpl>>();

	public RoleImpl() {
		this(null);
	}

	public RoleImpl(final String code) {
		super(Tables.ROLE, code, TranslationType.NAME);
	}

	public Set<Permission> getPermissions() {
		return Keys.getInstances(Permission.class, permissions);
	}
}