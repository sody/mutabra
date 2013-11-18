/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.common;

import org.mongodb.morphia.annotations.Embedded;
import com.mutabra.domain.Translatable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class Ability implements Translatable {
    public static final String BASENAME = "ability";

    private String code;
    private TargetType targetType;
    private int bloodCost;

    private List<Effect> effects = new ArrayList<Effect>();

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }

    public int getBloodCost() {
        return bloodCost;
    }

    public void setBloodCost(final int bloodCost) {
        this.bloodCost = bloodCost;
    }

    public List<Effect> getEffects() {
        return effects;
    }
}
