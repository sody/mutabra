package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbilityDisplay extends AbstractComponent implements ClientElement {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String id;

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String descriptionId;

    @Parameter(required = true, allowNull = false)
    private BattleCreature creature;

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleAbility ability;

    @Property
    private Effect effect;

    public String getClientId() {
        return id;
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public String getContainerClass() {
        return creature.isReady() ?
                "card disabled" :
                "card";
    }

    public String getCastLink() {
        return getResources().createEventLink("cast", creature, ability).toURI();
    }

    public String getTargetSelector() {
        final TargetType targetType = ability.getTargetType();

        final StringBuilder sideSelector = new StringBuilder("path");
        if (targetType.supportsEnemy() && !targetType.supportsFriend()) {
            sideSelector.append(".enemy");
        } else if (targetType.supportsFriend() && !targetType.supportsEnemy()) {
            sideSelector.append(".friend");
        }

        final StringBuilder selector = new StringBuilder();
        if (targetType.supportsEmpty()) {
            selector.append(sideSelector).append(".empty");
        }
        if (targetType.supportsHero()) {
            if (selector.length() > 0) {
                selector.append(",");
            }
            selector.append(sideSelector).append(".hero");
        }
        if (targetType.supportsCreature()) {
            if (selector.length() > 0) {
                selector.append(",");
            }
            selector.append(sideSelector).append(".creature");
        }
        if (selector.length() == 0) {
            selector.append(sideSelector);
        }

        return selector.toString();
    }

    @SetupRender
    void setup() {
        effect = ability.getEffects().get(0);
    }
}
