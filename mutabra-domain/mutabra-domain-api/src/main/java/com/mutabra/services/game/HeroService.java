package com.mutabra.services.game;

import com.mutabra.domain.game.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HeroService extends BaseEntityService<Hero> {

	Hero create(Account account);
}
