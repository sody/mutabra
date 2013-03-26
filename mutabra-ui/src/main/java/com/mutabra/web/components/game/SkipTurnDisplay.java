package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SkipTurnDisplay extends AbstractComponent {

    @Property
    @Parameter
    private BattleHero hero;

    public String getContainerClass() {
        return hero.isReady() ?
                "card disabled" :
                "card";
    }

    public String getCastLink() {
        return getResources().createEventLink("skip", hero).toAbsoluteURI();
    }

    public String getDescriptionSelector() {
        return "#" + SkipTurnDescription.ID;
    }
}
