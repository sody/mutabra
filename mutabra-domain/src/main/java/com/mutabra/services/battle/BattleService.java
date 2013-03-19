package com.mutabra.services.battle;

import com.mutabra.domain.battle.*;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService extends BaseEntityService<Battle> {

    void create(Hero hero1, Hero hero2);

    void start(Battle battle);

    void end(Battle battle);

    void endRound(Battle battle);

    void cast(Battle battle, BattleHero hero, BattleCard card, BattleTarget target);

    void cast(Battle battle, BattleCreature creature, BattleAbility ability, BattleTarget target);

    void skip(Battle battle, BattleHero hero);
}
