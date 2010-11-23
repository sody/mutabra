package com.noname.web.services;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ApplicationContext {

	Account getAccount();

	Hero getHero();

}
