package com.mutabra.web.pages.admin;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Permission;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.AccountDialog;
import com.mutabra.web.internal.Authority;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(Permission.MANAGE_APPLICATION)
public class Accounts extends AbstractPage {

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

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
