package com.mutabra.services.player;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HeroService extends BaseEntityService<Hero> {

	Hero create(Account account);
}
