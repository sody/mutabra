package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.RoleQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Roles extends AbstractPage {

	@InjectService("roleService")
	private BaseEntityService<Role, RoleQuery> roleService;

	public GridDataSource getRoleSource() {
		return new BaseEntityDataSource<Role>(roleService.query(), Role.class);
	}

	void onAdd() {
		final Role role = roleService.create();
		final String randomCode = new BigInteger(130, new SecureRandom()).toString(32);
		role.setCode(randomCode);
		roleService.save(role);
	}
}
