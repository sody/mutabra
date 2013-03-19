package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SkipTurnDisplay extends AbstractComponent {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String description;

    @Property
    @Parameter
    private BattleHero hero;

    public String getContainerClass() {
        return hero.isReady() ?
                "card disabled" :
                "card";
    }

    public String getCastLink() {
        return getResources().createEventLink("skipTurn", hero).toAbsoluteURI();
    }

    public String getDescriptionSelector() {
        return "#" + description;
    }
}
