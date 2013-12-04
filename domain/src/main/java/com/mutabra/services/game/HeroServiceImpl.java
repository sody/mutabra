/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.game;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountHero;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityServiceImpl;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroServiceImpl
        extends BaseEntityServiceImpl<Hero>
        implements HeroService {

    public HeroServiceImpl(final Datastore datastore) {
        super(datastore, Hero.class);
    }

    public Hero get(final Account account) {
        final AccountHero accountHero = account.getHero();
        return accountHero != null ? get(accountHero.getId()) : null;
    }

    public List<Hero> getAll(final Account account) {
        return query()
                .filter("account =", account)
                .asList();
    }

    @Override
    public void create(final Account account, final Hero hero, final Race race) {
        hero.setAccount(account);
        hero.getAppearance().setRace(race.getCode());

        //TODO: should be retrieved from level
        hero.getLevel().setCode("newbie");
        hero.getLevel().setRating(0);

        hero.setHealth(race.getHealth());
        hero.setMentalPower(race.getMentalPower());
        hero.getCards().addAll(race.getCards());

        save(hero);
    }

    public void enter(final Account account, final Hero hero) {
        account.setHero(new AccountHero(hero));
        datastore().save(account);
    }
}
