package com.noname.web.pages.security;

import com.mutabra.domain.security.Account;
import com.noname.game.User;
import com.noname.services.security.AccountService;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.pages.player.hero.HeroCreate;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Activate extends AbstractPage {

	@Property
	private String resultMessage;

	@Inject
	private AccountService accountService;

	Object onActivate(final String email, final String token) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(token)) {
			resultMessage = "Wrong activation parameters";
		} else {
			final Account account = accountService.getAccount(email);
			if (account == null || !token.equals(account.getToken())) {
				resultMessage = "Wrong activation parameters";
			} else {
				accountService.activateAccount(account);
				getUserService().initCurrentUser(new User(account));
				return HeroCreate.class;
			}
		}
		return null;
	}
}
