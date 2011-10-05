package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.RoleQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.Dialog;
import com.mutabra.web.components.admin.RoleDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Roles extends AbstractPage {

	@InjectService("roleService")
	private BaseEntityService<Role, RoleQuery> roleService;

	@InjectComponent
	private RoleDialog roleDialog;

	@Property
	private Role row;

	public GridDataSource getRoleSource() {
		return new BaseEntityDataSource<Role>(roleService.query(), Role.class);
	}

	Object onAdd() {
		return roleDialog.show(roleService.create());
	}

	Object onEdit(final Role role) {
		return roleDialog.show(role);
	}

	void onDelete(final Role role) {
		roleService.delete(role);
	}

	void onSuccess() {
		roleService.saveOrUpdate(roleDialog.getValue());
	}
}
