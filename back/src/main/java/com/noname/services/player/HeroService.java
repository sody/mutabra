package com.noname.services.player;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import com.noname.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 */
public interface HeroService extends BaseEntityService<Hero> {

	Hero createHero(Account account);

}
