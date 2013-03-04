package com.mutabra.domain.battle;

import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embeddable
public class BattleCardImpl implements BattleCard {

    private Long id;
    private BattleCardType type;
    private String code;
    private TargetType targetType;
    private int bloodCost;
    private List<Effect> effects = new ArrayList<Effect>();

    public Long getId() {
        return id;
    }

    public BattleCardType getType() {
        return type;
    }

    public void setType(final BattleCardType type) {
        this.type = type;
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

    public void assignId(final long id) {
        this.id = id;
    }
}
