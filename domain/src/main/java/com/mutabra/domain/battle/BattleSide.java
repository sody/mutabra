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
public enum BattleSide implements Translatable {
    YOUR,
    ENEMY;

    private static final String BASENAME = "battle-side";
    private final String code;

    private BattleSide() {
        code = name().replaceAll("([A-Z])", "-$1").toLowerCase();
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
