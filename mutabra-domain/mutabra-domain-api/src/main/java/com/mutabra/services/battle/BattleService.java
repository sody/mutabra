package com.mutabra.services.battle;

import com.mutabra.annotations.Transactional;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService extends BaseEntityService<Battle> {

    @Transactional
    void create(Hero hero1, Hero hero2);

    @Transactional
    void start(Battle battle);

    @Transactional
    void end(Battle battle);

    @Transactional
    void endRound(Battle battle);

    @Transactional
    void cast(Battle battle, BattleHero hero, BattleCard card, Position target);

    @Transactional
    void cast(Battle battle, BattleCreature creature, BattleAbility ability, Position target);

    @Transactional
    void skip(Battle battle, BattleHero hero);
}
