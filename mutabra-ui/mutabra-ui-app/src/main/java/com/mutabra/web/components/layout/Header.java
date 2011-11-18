package com.mutabra.web.components.layout;

import com.mutabra.domain.game.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Header {

	@Inject
	private AccountContext accountContext;

	public Account getAccount() {
		return accountContext.getAccount();
	}

	public Hero getHero() {
		return accountContext.getHero();
	}
}
