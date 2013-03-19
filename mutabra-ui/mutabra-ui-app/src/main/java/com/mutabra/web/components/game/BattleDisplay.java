package com.mutabra.web.components.game;

import com.mutabra.domain.battle.*;
import com.mutabra.services.battle.BattleField;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleDisplay {

    @Property
    @Parameter
    private BattleField field;

    @Property
    private BattleField.Point point;

    @Property
    private BattleHero hero;

    @Property
    private BattleCard card;

    @Property
    private BattleCreature creature;

    @Property
    private BattleAbility ability;

    public boolean isEnemy() {
        return !field.getSelf().equals(hero);
    }

    public String getActiveClass() {
        return isEnemy() ? "" : " active";
    }

}
