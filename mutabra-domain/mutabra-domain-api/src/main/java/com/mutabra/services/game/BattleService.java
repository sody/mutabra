package com.mutabra.services.game;

import com.mutabra.domain.game.Battle;
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
}
