/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleCard implements Translatable {

    private Long id;
    private String code;
    private BattleCardType type;
    private TargetType targetType;
    private int bloodCost;

    private List<Effect> effects = new ArrayList<Effect>();

    @Transient
    private BattleHero hero;

    public Long getId() {
        return id;
    }

    public BattleHero getHero() {
        return hero;
    }

    public BattleCardType getType() {
        return type;
    }

    public void setType(final BattleCardType type) {
        this.type = type;
    }

    public String getBasename() {
        return Card.BASENAME;
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
    void assignHero(final BattleHero hero) {
        this.hero = hero;
    }

    void assignId(final long id) {
        this.id = id;
    }
}
