/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.game;

import com.mutabra.domain.CodeUtils;
import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum HeroAppearancePart implements Translatable {
    EARS(1),
    FACE(7),
    EYES(4),
    EYEBROWS(1),
    NOSE(1),
    MOUTH(3),
    HAIR(4),
    FACIAL_HAIR(1),
    NAME(0),
    RACE(0),
    SEX(0);

    public static final String BASENAME = "face";

    private final String code;
    private final int count;

    private HeroAppearancePart(final int count) {
        this.count = count;

        code = CodeUtils.generateCode(this);
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getBasename() {
        return BASENAME;
    }

    @Override
    public String getCode() {
        return code;
    }
}
