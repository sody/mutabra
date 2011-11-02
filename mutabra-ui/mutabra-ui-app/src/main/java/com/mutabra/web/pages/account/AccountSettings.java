package com.mutabra.web.pages.account;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.services.AccountContext;
import com.mutabra.web.services.AccountManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.annotations.Deny;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Deny(Authorities.STATUS_ANONYMOUS)
public class AccountSettings extends AbstractPage {

	@Property
	private String email;

	@Property
	private String password;

	@Property
	private String confirmPassword;

	@Inject
	private AccountContext accountContext;

	@Inject
	private AccountManager accountManager;

	@InjectService("accountService")
	private BaseEntityService<Account, AccountQuery> accountService;

	public Account getValue() {
		return accountContext.getAccount();
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "accountForm")
	void save() {
		accountService.saveOrUpdate(getValue());
		//todo: add success notification
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "changeEmailForm")
	void changeEmail() {
		//todo: validate if if email exist, if new email differs from current and if new email are not exist
		accountManager.changeEmail(getValue().getEmail(), email);
		//todo: add success notification
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "changePasswordForm")
	void changePassword() {
		//todo: validate if email exist, if passwords match each other...
		accountManager.changePassword(getValue().getEmail(), password);
		//todo: add success notification
	}
}
