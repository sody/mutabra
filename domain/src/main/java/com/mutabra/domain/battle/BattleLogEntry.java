/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Effect;
import org.mongodb.morphia.annotations.Embedded;

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

    public List<BattleLogParameter> getParameters() {
        return parameters;
    }
}
