/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.services.security;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import org.greatage.security.DefaultAuthentication;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GameUser extends DefaultAuthentication {
	private final Account account;
	private final Hero hero;

	public GameUser(final Account account) {
		this(account, null);
	}

	public GameUser(final Account account, final Hero hero) throws IllegalArgumentException {
		super(account.getEmail(), account.getPassword(), account.createAuthorities());
		this.account = account;
		this.hero = hero;
	}

	public Account getAccount() {
		return account;
	}

	public Hero getHero() {
		return hero;
	}
}
