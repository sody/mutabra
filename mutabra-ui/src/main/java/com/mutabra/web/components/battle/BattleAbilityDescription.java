package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.common.Effect;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleAbilityDescription extends AbstractComponent implements ClientElement {
    public static final String ID_PREFIX = "d_ability_";

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleAbility ability;

    @Property
    private Effect effect;

    public String getClientId() {
        return ID_PREFIX + encode(BattleAbility.class, ability);
    }

    public String getName() {
        return label(ability, BattleAbility.NAME);
    }

    public String getDescription() {
        return label(ability, BattleAbility.DESCRIPTION);
    }

    @SetupRender
    void setup() {
        effect = ability.getEffects().get(0);
    }
}
