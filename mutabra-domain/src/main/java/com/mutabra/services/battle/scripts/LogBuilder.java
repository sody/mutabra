package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.*;

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
        entry.getParameters().put(key, new BattleLogParameter(side, position));
        return this;
    }

    public LogBuilder parameter(final String key, final BattleUnit battleUnit) {
        entry.getParameters().put(key, new BattleLogParameter(battleUnit));
        return this;
    }

    public LogBuilder parameter(final String key, final BattleSpell battleSpell) {
        entry.getParameters().put(key, new BattleLogParameter(battleSpell));
        return this;
    }

    public LogBuilder parameter(final String key, final BattleEffect battleEffect) {
        entry.getParameters().put(key, new BattleLogParameter(battleEffect));
        return this;
    }

    public LogBuilder parameter(final String key, final Object value) {
        entry.getParameters().put(key, new BattleLogParameter(value));
        return this;
    }

    public void build(final Battle battle) {
        battle.getLog().add(entry);
    }
}
