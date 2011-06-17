package com.mutabra.domain.security;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable
public class PermissionImpl extends CodedEntityImpl implements Permission {

	@Persistent
	private Set<Role> roles = new HashSet<Role>();

	public PermissionImpl() {
		super("PERMISSION", TranslationType.STANDARD);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAuthority() {
		return PERMISSION_PREFIX + getCode().toUpperCase();
	}

}