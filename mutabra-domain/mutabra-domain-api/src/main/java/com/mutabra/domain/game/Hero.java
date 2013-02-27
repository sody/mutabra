package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Hero extends BaseEntity {

    Account getAccount(); //LAZY REF

    void setAccount(Account account);

    boolean isActive();

    void setActive(boolean active);

    HeroLevel getLevel();

    HeroAppearance getAppearance();

    int getHealth();

    void setHealth(int health);

    int getMentalPower();

    void setMentalPower(int mentalPower);

    List<String> getCards();

    Date getLastActive();

    void setLastActive(Date lastActive);
}
