/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.services;

import com.noname.domain.security.Account;
import org.greatage.security.DefaultAuthentication;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GameUser extends DefaultAuthentication {
	private final Account account;

	public GameUser(final Account account) throws IllegalArgumentException {
		super(account.getEmail(), account.getPassword(), account.createAuthorities());
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}
