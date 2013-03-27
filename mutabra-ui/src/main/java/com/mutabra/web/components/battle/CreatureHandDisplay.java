package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CreatureHandDisplay extends AbstractComponent implements ClientElement {
    public static final String ID_PREFIX = "h_creature";

    @Property
    @Parameter(required = true, allowNull = false)
    private BattleCreature creature;

    @Property
    private BattleAbility ability;

    public String getClientId() {
        return ID_PREFIX + encode(BattleCreature.class, creature);
    }

    public String getContainerClass() {
        return "row actions";
    }
}
