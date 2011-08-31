package com.mutabra.domain.security;

import com.mutabra.domain.CodedEntity;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Permission extends CodedEntity {

	String PERMISSION_PREFIX = "PERMISSION_";

	Set<Role> getRoles();

	void setRoles(Set<Role> roles);

	String getAuthority();
}
