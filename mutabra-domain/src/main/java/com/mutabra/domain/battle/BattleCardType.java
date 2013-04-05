package com.mutabra.domain.battle;

import com.mutabra.domain.CodeUtils;
import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum BattleCardType implements Translatable {
    DECK,
    HAND,
    GRAVEYARD;

    private static final String BASENAME = "battle-card-type";
    private final String code;

    private BattleCardType() {
        code = CodeUtils.generateCode(this);
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
