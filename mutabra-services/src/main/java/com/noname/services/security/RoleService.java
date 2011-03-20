package com.noname.services.security;

import com.mutabra.domain.security.Role;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface RoleService extends CodedEntityService<Role> {

	Role getAdminRole();

	Role getUserRole();

	Role getPendingRole();

}
