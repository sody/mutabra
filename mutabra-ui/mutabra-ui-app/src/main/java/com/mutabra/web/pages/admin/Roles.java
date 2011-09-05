package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.RoleQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Roles extends AbstractPage {

	@InjectService("roleService")
	private BaseEntityService<Role, RoleQuery> roleService;

	private Role role;

	public GridDataSource getRoleSource() {
		return new BaseEntityDataSource<Role>(roleService.query(), Role.class);
	}

	public Role getRole() {
		if (role == null) {
			role = roleService.create();
		}
		return role;
	}

	void onSuccess() {
//		final String randomCode = new BigInteger(130, new SecureRandom()).toString(32);
//		role.setCode(randomCode);
		roleService.save(role);
	}
}
