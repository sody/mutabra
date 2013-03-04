package com.mutabra.domain.game;

import javax.persistence.Embeddable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embeddable
public class HeroAppearanceImpl implements HeroAppearance {

    private String name;
    private String race;
    private String face;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }
}
