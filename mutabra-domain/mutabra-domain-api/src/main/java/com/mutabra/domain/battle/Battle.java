package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Battle extends BaseEntity {

    boolean isActive();

    void setActive(boolean active);

    int getRound();

    void setRound(int round);

    Date getStartedAt();

    void setStartedAt(Date startedAt);

    List<BattleHero> getHeroes();

    List<BattleEffect> getEffects();

    /* HELPERS */
    boolean isAllReady();

    BattleHero createHero();

    BattleCard createCard();

    BattleCreature createCreature();

    BattleAbility createAbility();

    BattleEffect createEffect();
}
