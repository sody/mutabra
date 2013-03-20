package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Transient;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleAbility {

    private Long id;
    private String code;
    private TargetType targetType;
    private int bloodCost;

    private List<Effect> effects = new ArrayList<Effect>();

    @Transient
    private BattleCreature creature;

    public Long getId() {
        return id;
    }

    public BattleCreature getCreature() {
        return creature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
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

    /* HELPER METHODS */
    void assignCreature(final BattleCreature creature) {
        this.creature = creature;
    }

    void assignId(final long id) {
        this.id = id;
    }
}
