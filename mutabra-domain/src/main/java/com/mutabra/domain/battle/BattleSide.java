package com.mutabra.domain.battle;

import com.mutabra.domain.CodeUtils;
import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum BattleSide implements Translatable {
    YOUR,
    ENEMY;

    private static final String BASENAME = "battle-side";
    private final String code;

    private BattleSide() {
        code = CodeUtils.generateCode(this);
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
