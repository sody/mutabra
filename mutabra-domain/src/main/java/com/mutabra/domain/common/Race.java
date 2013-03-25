package com.mutabra.domain.common;

import com.google.code.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "races", noClassnameStored = true)
public class Race extends CodedEntity {

    private int health;
    private int mentalPower;

    private List<String> cards = new ArrayList<String>();

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public int getMentalPower() {
        return mentalPower;
    }

    public void setMentalPower(final int mentalPower) {
        this.mentalPower = mentalPower;
    }

    public List<String> getCards() {
        return cards;
    }
}
