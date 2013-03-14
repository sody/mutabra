package com.mutabra.domain.game;

import com.google.code.morphia.annotations.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class HeroAppearance {

    private String name;
    private String race;
    private String face;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(final String race) {
        this.race = race;
    }

    public String getFace() {
        return face;
    }

    public void setFace(final String face) {
        this.face = face;
    }
}
