/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
import com.mutabra.domain.battle.BattlePosition;
import com.mutabra.domain.battle.BattleSide;
import com.mutabra.domain.battle.BattleSpell;
import com.mutabra.domain.battle.BattleUnit;

/**
 * @author Ivan Khalopik
 */
public class LogBuilder {
    private final BattleLogEntry entry;

    public LogBuilder(final BattleEffect battleEffect, final String variant) {
        this(battleEffect.getCode(), variant);
    }

    public LogBuilder(final BattleEffect battleEffect) {
        this(battleEffect.getCode());
    }

    public LogBuilder(final String code, final String variant) {
        this(code + "." + variant);
    }

    public LogBuilder(final String code) {
        entry = new BattleLogEntry(code);
    }

    public LogBuilder parameter(final String key, final BattleSide side, final BattlePosition position) {
        entry.getParameters().add(new BattleLogParameter(key, side, position));
        return this;
    }

    public LogBuilder parameter(final String key, final BattleUnit battleUnit) {
        entry.getParameters().add(new BattleLogParameter(key, battleUnit));
        return this;
    }

    public LogBuilder parameter(final String key, final BattleSpell battleSpell) {
        entry.getParameters().add(new BattleLogParameter(key, battleSpell));
        return this;
    }

    public LogBuilder parameter(final String key, final BattleEffect battleEffect) {
        entry.getParameters().add(new BattleLogParameter(key, battleEffect));
        return this;
    }

    public LogBuilder parameter(final String key, final Object value) {
        entry.getParameters().add(new BattleLogParameter(key, value));
        return this;
    }

    public void build(final Battle battle) {
        battle.getLog().add(entry);
    }
}
