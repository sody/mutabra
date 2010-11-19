package com.noname.web.pages.security;

import com.noname.domain.security.Account;
import com.noname.services.security.AccountService;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.services.AuthorityConstants;
import com.noname.web.services.GameUser;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.UserContext;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_USER)
public class Settings extends AbstractPage {

	@Inject
	private AccountService accountService;

	@Inject
	private UserContext<GameUser> userContext;

	@Component
	private Form changeEmailForm;

	@Component
	private Form changePasswordForm;

	@Component
	private Form changeInformationForm;

	@Property
	private Account account;

	@Property
	private String email;

	@Property
	private String password;

	@Property
	private String confirmPassword;

	void onActivate() {
		account = userContext.getUser().getAccount();
	}

	void onChangeEmail() {
		//todo: add implementation
	}

	void onChangePassword() {
		//todo: add implementation
	}

	void onChangeInformation() {
		if (changeInformationForm.isValid()) {
			accountService.update(account);
		}
	}
}
