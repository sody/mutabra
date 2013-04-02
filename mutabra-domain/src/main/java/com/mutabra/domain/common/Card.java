package com.mutabra.domain.common;

import com.google.code.morphia.annotations.Entity;
import com.mutabra.domain.CodedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(value = "cards", noClassnameStored = true)
public class Card extends CodedEntity {
    public static final String BASENAME = "card";

    private TargetType targetType;
    private int bloodCost;

    private List<Effect> effects = new ArrayList<Effect>();

    @Override
    public String getBasename() {
        return BASENAME;
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
