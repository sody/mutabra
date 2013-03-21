package com.mutabra.web.services;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AccountContext {

    Account getAccount();

    Hero getHero();

    Battle getBattle();
}
