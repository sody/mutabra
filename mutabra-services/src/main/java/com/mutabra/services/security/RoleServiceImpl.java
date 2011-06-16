package com.mutabra.services.security;

import com.mutabra.domain.security.Role;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.TranslationService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RoleServiceImpl extends CodedEntityServiceImpl<Role, RoleQuery> implements RoleService {
	private static final String ADMIN_ROLE_CODE = "ADMIN";
	private static final String USER_ROLE_CODE = "USER";
	private static final String PENDING_ROLE_CODE = "PENDING";

	public RoleServiceImpl(final EntityRepository repository) {
		super(repository, Role.class, RoleQuery.class);
	}

	public Role getAdminRole() {
		return get(ADMIN_ROLE_CODE);
	}

	public Role getUserRole() {
		return get(USER_ROLE_CODE);
	}

	public Role getPendingRole() {
		return get(PENDING_ROLE_CODE);
	}
}
