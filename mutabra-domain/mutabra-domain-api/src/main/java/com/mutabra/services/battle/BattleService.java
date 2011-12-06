package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleAction;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.Position;
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
	void startBattle(Hero hero1, Hero hero2);

	@Transactional
	void endRound(Battle battle);

	@Transactional
	void registerAction(BattleCard card, Position target);

	List<BattleField> getBattleField(Hero hero, Battle battle);

}
