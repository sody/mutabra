package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleLogEntry implements Translatable {
    private String code;

    private List<BattleLogParameter> parameters = new ArrayList<BattleLogParameter>();

    public String getBasename() {
        return Effect.BASENAME;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public List<BattleLogParameter> getParameters() {
        return parameters;
    }
}
