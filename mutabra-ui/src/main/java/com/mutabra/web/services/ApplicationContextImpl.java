package com.mutabra.web.services;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.game.User;
import com.mutabra.services.player.HeroService;
import com.mutabra.services.security.AccountService;
import com.mutabra.services.security.GameSecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationContextImpl implements ApplicationContext {
	private final GameSecurityContext securityContext;
	private final AccountService accountService;
	private final HeroService heroService;

	private Account account;
	private Hero hero;

	public ApplicationContextImpl(final GameSecurityContext securityContext,
								  final AccountService accountService,
								  final HeroService heroService) {
		this.securityContext = securityContext;
		this.accountService = accountService;
		this.heroService = heroService;
	}

	public Account getAccount() {
		if (account == null) {
			final User user = securityContext.getCurrentUser();
			if (user != null && user.getAccount() != null) {
				account = accountService.get(user.getAccount().getId());
			}
		}
		return account;
	}

	public Hero getHero() {
		if (hero == null) {
			final User user = securityContext.getCurrentUser();
			if (user != null && user.getHero() != null) {
				hero = heroService.get(user.getHero().getId());
			}
		}
		return hero;
	}
}
