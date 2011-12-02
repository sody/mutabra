package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;
import org.greatage.domain.annotations.Transactional;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService extends BaseEntityService<Battle> {

	@Transactional
	Battle createBattle(Hero hero1, Hero hero2);

	@Transactional
	Battle startRound(Battle battle);

	List<BattleField> getBattleField(Hero hero, Battle battle);

}
