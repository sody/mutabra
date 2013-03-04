package com.mutabra.domain.game;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.HERO)
public class HeroImpl extends BaseEntityImpl implements Hero {

    @Parent
    private Key<AccountImpl> account;

    private boolean active;
    private HeroLevel level = new HeroLevelImpl();
    private HeroAppearance appearance = new HeroAppearanceImpl();
    private int health;
    private int mentalPower;
    private List<String> cards =  new ArrayList<String>();

    @Indexed
    private Date lastActive;

    public Account getAccount() {
        return Keys.getInstance(account);
    }

    public void setAccount(final Account account) {
        this.account = Keys.getKey(account);
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

    @Override
    public Key<?> getParentKey() {
        return account;
    }
}
