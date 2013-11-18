/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleEffect;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EffectScript {

    void execute(Battle battle, BattleEffect effect);
}
