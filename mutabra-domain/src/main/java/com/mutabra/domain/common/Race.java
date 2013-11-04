package com.mutabra.domain.common;

import org.mongodb.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "races", noClassnameStored = true)
public class Race extends CodedEntity {
    public static final String BASENAME = "race";

    private int health;
    private int mentalPower;

    private List<String> cards = new ArrayList<String>();

    @Override
    public String getBasename() {
        return BASENAME;
    }

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
