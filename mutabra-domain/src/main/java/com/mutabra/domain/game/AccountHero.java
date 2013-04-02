package com.mutabra.domain.game;

import com.google.code.morphia.annotations.Embedded;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class AccountHero {

    private ObjectId id;

    @Embedded
    private HeroAppearance appearance = new HeroAppearance();

    public AccountHero() {
    }

    public AccountHero(final Hero hero) {
        id = hero.getId();
        appearance.setName(hero.getAppearance().getName());
        appearance.setFace(hero.getAppearance().getFace());
        appearance.setRace(hero.getAppearance().getRace());
    }

    public ObjectId getId() {
        return id;
    }

    public HeroAppearance getAppearance() {
        return appearance;
    }
}
