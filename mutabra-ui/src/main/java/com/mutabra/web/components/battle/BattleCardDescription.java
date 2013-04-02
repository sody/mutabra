package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleCard;
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
public class BattleCardDescription extends AbstractComponent implements ClientElement {
    public static final String ID_PREFIX = "d_card_";

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleCard card;

    @Property
    private Effect effect;

    public String getClientId() {
        return ID_PREFIX + encode(BattleCard.class, card);
    }

    public String getName() {
        return label(card, BattleCard.NAME);
    }

    public String getDescription() {
        return label(card, BattleCard.DESCRIPTION);
    }

    @SetupRender
    void setup() {
        effect = card.getEffects().get(0);
    }
}
