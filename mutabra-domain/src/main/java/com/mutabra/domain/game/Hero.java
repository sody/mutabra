package com.mutabra.domain.game;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import com.mutabra.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity("heroes")
public class Hero extends BaseEntity {

    @Reference
    private Account account;

    private boolean active;
    private HeroLevel level = new HeroLevel();
    private HeroAppearance appearance = new HeroAppearance();
    private int health;
    private int mentalPower;

    private List<String> cards = new ArrayList<String>();

    @Indexed
    private Date lastActive;

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
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

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(final Date lastActive) {
        this.lastActive = lastActive;
    }
}
