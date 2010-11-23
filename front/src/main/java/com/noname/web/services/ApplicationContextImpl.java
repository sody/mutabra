package com.noname.web.services;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import com.noname.services.player.HeroService;
import com.noname.services.security.AccountService;
import com.noname.web.services.security.GameUser;
import org.greatage.security.SecurityContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationContextImpl implements ApplicationContext {
	private final SecurityContext securityContext;
	private final AccountService accountService;
	private final HeroService heroService;

	private Account account;
	private Hero hero;

	public ApplicationContextImpl(final SecurityContext securityContext,
								  final AccountService accountService,
								  final HeroService heroService) {
		this.securityContext = securityContext;
		this.accountService = accountService;
		this.heroService = heroService;
	}

	public Account getAccount() {
		if (account == null) {
			final GameUser user = (GameUser) securityContext.getAuthentication();
			if (user != null) {
				account = accountService.get(user.getAccount().getId());
			}
		}
		return account;
	}

	public Hero getHero() {
		if (hero == null) {
			final GameUser user = (GameUser) securityContext.getAuthentication();
			if (user != null && user.getHero() != null) {
				hero = heroService.get(user.getHero().getId());
			}
		}
		return hero;
	}
}
