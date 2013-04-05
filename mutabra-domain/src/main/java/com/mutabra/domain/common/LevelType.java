package com.mutabra.domain.common;

import com.mutabra.domain.CodeUtils;
import com.mutabra.domain.Translatable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum LevelType implements Translatable {
    HERO,
    CARD;

    private static final String BASENAME = "level-type";
    private final String code;

    private LevelType() {
        code = CodeUtils.generateCode(this);
    }

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }
}
