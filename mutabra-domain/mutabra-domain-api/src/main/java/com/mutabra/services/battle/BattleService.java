package com.mutabra.services.battle;

import com.mutabra.annotations.Transactional;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Castable;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService extends BaseEntityService<Battle> {

    @Transactional
    void startBattle(Hero hero1, Hero hero2);

    @Transactional
    void endRound(Battle battle);

    @Transactional
    void registerAction(Battle battle, BattleUnit caster, Castable castable, Position target);

    @Transactional
    void skipTurn(Battle battle, BattleHero hero);

    List<BattleField> getBattleField(Hero hero, Battle battle);

}
