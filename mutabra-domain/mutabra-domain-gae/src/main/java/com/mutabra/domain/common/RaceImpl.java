package com.mutabra.domain.common;

import com.googlecode.objectify.annotation.Indexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.RACE)
public class RaceImpl extends BaseEntityImpl implements Race {

    @Indexed
    private String code;

    private int health;
    private int mentalPower;
    private List<String> cards = new ArrayList<String>();


    public String getCode() {
        return code;
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
