package com.mutabra.services.battle;

import com.mutabra.domain.battle.*;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService extends BaseEntityService<Battle> {

    Battle get(Account account);

    void create(Hero hero);

    void apply(Battle battle, Hero hero);

    List<Battle> findBattles();

    void cast(Battle battle, BattleCard card, BattleTarget target);

    void cast(Battle battle, BattleAbility ability, BattleTarget target);

    void skip(Battle battle, BattleHero hero);
}
