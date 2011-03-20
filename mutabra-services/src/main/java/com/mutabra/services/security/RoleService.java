package com.mutabra.services.security;

import com.mutabra.domain.security.Role;
import com.mutabra.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface RoleService extends CodedEntityService<Role> {

	Role getAdminRole();

	Role getUserRole();

	Role getPendingRole();

}
