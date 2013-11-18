/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.game;

import org.mongodb.morphia.Datastore;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountHero;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityServiceImpl;

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

    public Hero create(final Account account, final Race race, final Face face, final String name) {
        final Hero hero = new Hero();
        hero.setAccount(account);
        hero.getAppearance().setRace(race.getCode());
        hero.getAppearance().setFace(face.getCode());
        hero.getAppearance().setName(name);

        //TODO: should be retrieved from level
        hero.getLevel().setCode("newbie");
        hero.getLevel().setRating(0);

        hero.setHealth(race.getHealth());
        hero.setMentalPower(race.getMentalPower());
        hero.getCards().addAll(race.getCards());

        save(hero);

        return hero;
    }

    public void enter(final Account account, final Hero hero) {
        account.setHero(new AccountHero(hero));
        datastore().save(account);
    }
}
