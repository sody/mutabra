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
    EARS,
    FACE,
    EYES,
    EYEBROWS,
    NOSE,
    MOUTH,
    HAIR,
    FACIAL_HAIR,
    NAME,
    RACE,
    SEX;

    public static final String BASENAME = "face";

    private final String code;

    private HeroAppearancePart() {
        code = CodeUtils.generateCode(this);
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
