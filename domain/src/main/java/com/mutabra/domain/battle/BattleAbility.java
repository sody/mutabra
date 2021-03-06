/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleAbility implements BattleSpell, Translatable {

    private Long id;
    private String code;
    private TargetType targetType;
    private int bloodCost;

    private List<Effect> effects = new ArrayList<Effect>();

    @Transient
    private BattleCreature creature;

    protected BattleAbility() {
    }

    public BattleAbility(final BattleCreature creature) {
        this.creature = creature;
        this.id = creature.getHero().getBattle().nextId();
    }

    public Long getId() {
        return id;
    }

    public BattleCreature getUnit() {
        return creature;
    }

    public String getBasename() {
        return Ability.BASENAME;
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

    public boolean isCard() {
        return false;
    }

    /* HELPER METHODS */
    void assignCreature(final BattleCreature creature) {
        this.creature = creature;
    }
}
