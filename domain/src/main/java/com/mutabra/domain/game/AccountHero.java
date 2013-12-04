/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.game;

import org.mongodb.morphia.annotations.Embedded;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class AccountHero {

    private ObjectId id;

    private HeroAppearance appearance = new HeroAppearance();

    public AccountHero() {
    }

    public AccountHero(final Hero hero) {
        id = hero.getId();
        appearance.fill(hero.getAppearance());
    }

    public ObjectId getId() {
        return id;
    }

    public HeroAppearance getAppearance() {
        return appearance;
    }
}
