package com.noname.web.services;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import com.noname.game.User;
import com.noname.services.player.HeroService;
import com.noname.services.security.AccountService;
import com.noname.services.security.GameSecurityContext;

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
