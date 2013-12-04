/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.game;

import com.mutabra.domain.common.Sex;
import org.mongodb.morphia.annotations.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class HeroAppearance {

    private String name;
    private String race;
    private Sex sex;
    private int face;
    private int eyes;
    private int eyebrows;
    private int ears;
    private int nose;
    private int mouth;
    private int hair;
    private int facialHair;

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(final Sex sex) {
        this.sex = sex;
    }

    public int getFace() {
        return face;
    }

    public void setFace(final int face) {
        this.face = face;
    }

    public int getEyes() {
        return eyes;
    }

    public void setEyes(final int eyes) {
        this.eyes = eyes;
    }

    public int getEyebrows() {
        return eyebrows;
    }

    public void setEyebrows(final int eyebrows) {
        this.eyebrows = eyebrows;
    }

    public int getEars() {
        return ears;
    }

    public void setEars(final int ears) {
        this.ears = ears;
    }

    public int getNose() {
        return nose;
    }

    public void setNose(final int nose) {
        this.nose = nose;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(final int mouth) {
        this.mouth = mouth;
    }

    public int getHair() {
        return hair;
    }

    public void setHair(final int hair) {
        this.hair = hair;
    }

    public int getFacialHair() {
        return facialHair;
    }

    public void setFacialHair(final int facialHair) {
        this.facialHair = facialHair;
    }

    public void fill(final HeroAppearance appearance) {
        this.name = appearance.name;
        this.race = appearance.race;
        this.sex = appearance.sex;
        this.face = appearance.face;
        this.eyes = appearance.eyes;
        this.eyebrows = appearance.eyebrows;
        this.ears = appearance.ears;
        this.nose = appearance.nose;
        this.mouth = appearance.mouth;
        this.hair = appearance.hair;
        this.facialHair = appearance.facialHair;
    }
}
