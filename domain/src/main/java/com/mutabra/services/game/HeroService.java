/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.game;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HeroService extends BaseEntityService<Hero> {

    Hero get(Account account);

    List<Hero> getAll(Account account);

    void create(Account account, Hero hero, Race race);

    void enter(Account account, Hero hero);
}
