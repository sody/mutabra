package com.mutabra.domain.security;

import com.mutabra.domain.CodedEntity;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Role extends CodedEntity {

	Set<Permission> getPermissions();
}
