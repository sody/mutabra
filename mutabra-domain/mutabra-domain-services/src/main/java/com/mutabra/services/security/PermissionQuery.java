package com.mutabra.services.security;

import com.mutabra.domain.security.Permission;
import com.mutabra.services.CodedEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PermissionQuery extends CodedEntityQuery<Permission, PermissionQuery> {

	public PermissionQuery(final EntityRepository repository) {
		super(repository, Permission.class);
	}
}
