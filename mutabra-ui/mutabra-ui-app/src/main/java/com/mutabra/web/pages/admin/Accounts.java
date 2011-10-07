package com.mutabra.web.pages.admin;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.AccountDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Accounts extends AbstractPage {

	@InjectService("accountService")
	private BaseEntityService<Account, AccountQuery> accountService;

	@InjectComponent
	private AccountDialog accountDialog;

	@Property
	private Account row;

	public GridDataSource getAccountSource() {
		return new BaseEntityDataSource<Account>(accountService.query(), Account.class);
	}

	Object onAdd() {
		return accountDialog.show(accountService.create());
	}

	Object onEdit(final Account account) {
		return accountDialog.show(account);
	}

	void onDelete(final Account account) {
		accountService.delete(account);
	}

	void onSuccess() {
		accountService.saveOrUpdate(accountDialog.getValue());
	}
}
