/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.game;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountHero;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.services.BaseEntityServiceImpl;
import org.mongodb.morphia.Datastore;

import java.util.List;
import java.util.Random;

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
    public void randomize(final Hero hero) {
        final HeroAppearance appearance = hero.getAppearance();
        final Random random = new Random();

        for (HeroAppearancePart part : HeroAppearancePart.values()) {
            final Integer count = part.getCount();
            final int value = count > 1 ? random.nextInt(count) : 0;

            switch (part) {
                case EARS:
                    appearance.setEars(value);
                    break;
                case FACE:
                    appearance.setFace(value);
                    break;
                case EYES:
                    appearance.setEyes(value);
                    break;
                case EYEBROWS:
                    appearance.setEyebrows(value);
                    break;
                case NOSE:
                    appearance.setNose(value);
                    break;
                case MOUTH:
                    appearance.setMouth(value);
                    break;
                case HAIR:
                    appearance.setHair(value);
                    break;
                case FACIAL_HAIR:
                    appearance.setFacialHair(value);
                    break;
            }
        }
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
