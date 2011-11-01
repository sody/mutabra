package com.mutabra.web.components.menu;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.web.pages.Index;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserMenu {

	@Inject
	private AccountContext accountContext;

	@Inject
	private SecurityContext securityContext;

	public Account getAccount() {
		return accountContext.getAccount();
	}

	public Hero getHero() {
		return accountContext.getHero();
	}

	public String getUserName() {
		final Account account = getAccount();
		if (account.getName() != null) {
			return account.getName();
		}
		if (account.getEmail() != null) {
			return account.getEmail().replaceFirst("@.*$", "");
		}
		return "<unknown>";
	}

	Object onSignOut() {
		securityContext.signOut();
		return Index.class;
	}
}