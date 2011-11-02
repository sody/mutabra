package com.mutabra.web.pages.admin;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.AccountImpl;
import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.services.security.RoleQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.AccountDialog;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.annotations.Allow;

import java.util.HashSet;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_ADMIN)
public class Accounts extends AbstractPage {

	@InjectService("accountService")
	private BaseEntityService<Account, AccountQuery> accountService;

	@InjectService("roleService")
	private BaseEntityService<Role, RoleQuery> roleService;

	@InjectComponent
	private AccountDialog entityDialog;

	@Property
	private Account row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Account>(accountService.query(), Account.class);
	}

	Object onAdd() {
		return entityDialog.show(accountService.create());
	}

	Object onEdit(final Account account) {
		return entityDialog.show(account);
	}

	void onDelete(final Account account) {
		accountService.delete(account);
	}

	Object onSuccess() {
		accountService.saveOrUpdate(entityDialog.getValue());
		return this;
	}
}
