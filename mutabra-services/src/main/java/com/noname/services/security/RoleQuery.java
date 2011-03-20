package com.noname.services.security;

import com.mutabra.domain.security.Role;
import com.noname.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RoleQuery extends CodedEntityQuery<Role, RoleQuery> implements RoleFilter {

	public RoleQuery() {
		super(Role.class);
	}

}
