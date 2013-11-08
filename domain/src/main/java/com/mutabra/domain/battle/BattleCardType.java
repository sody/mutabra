/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

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
        code = name().replaceAll("([A-Z])", "-$1").toLowerCase();
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
