package com.mutabra.web.pages.admin;

import com.mutabra.domain.game.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.AccountDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresAuthentication
@RequiresPermissions("account:view")
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

	@OnEvent(value = "add")
	Object addAccount() {
		return entityDialog.show(accountService.create());
	}

	@OnEvent(value = "edit")
	Object editAccount(final Account account) {
		return entityDialog.show(account);
	}

	@OnEvent(value = "delete")
	@RequiresPermissions("account:edit")
	void deleteAccount(final Account account) {
		accountService.delete(account);
	}

	@OnEvent(value = EventConstants.SUCCESS)
	@RequiresPermissions("account:edit")
	Object saveAccount() {
		accountService.saveOrUpdate(entityDialog.getValue());
		return this;
	}
}
