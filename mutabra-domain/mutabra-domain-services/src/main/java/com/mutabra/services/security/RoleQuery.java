package com.mutabra.services.security;

import com.mutabra.domain.security.Role;
import com.mutabra.services.CodedEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RoleQuery extends CodedEntityQuery<Role, RoleQuery> {

	public RoleQuery(final EntityRepository repository) {
		super(repository, Role.class);
	}
}
