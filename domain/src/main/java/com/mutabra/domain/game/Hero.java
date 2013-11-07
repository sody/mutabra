package com.mutabra.domain.game;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import com.mutabra.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "heroes", noClassnameStored = true)
public class Hero extends BaseEntity {

    @Reference
    private Account account;

    private HeroLevel level = new HeroLevel();
    private HeroAppearance appearance = new HeroAppearance();
    private int health;
    private int mentalPower;

    private List<String> cards = new ArrayList<String>();

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public HeroLevel getLevel() {
        return level;
    }

    public HeroAppearance getAppearance() {
        return appearance;
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
