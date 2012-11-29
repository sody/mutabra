package com.mutabra.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Effect;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.11
 */
public interface ScriptContext {

    Battle getBattle();

    BattleUnit getCaster();

    Effect getEffect();

    Position getTarget();

    List<BattleField> getTargets();

}
