package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Effect;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleLogEntry implements Translatable {
    private String code;

    private Map<String, BattleLogParameter> parameters = new HashMap<String, BattleLogParameter>();

    public BattleLogEntry() {
    }

    public BattleLogEntry(final String code) {
        this.code = code;
    }

    public String getBasename() {
        return Effect.BASENAME;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Map<String, BattleLogParameter> getParameters() {
        return parameters;
    }
}
