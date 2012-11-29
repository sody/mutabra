package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.IdUtils;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SkipTurnDisplay extends AbstractComponent implements ClientElement {

    @Property
    @Parameter
    private BattleHero hero;

    public String getClientId() {
        return IdUtils.generateSkipId();
    }

    public String getContainerClass() {
        return hero.isExhausted() ?
                "card disabled" :
                "card";
    }

    public String getDescriptionSelector() {
        return "#" + IdUtils.generateSkipDescriptionId();
    }

    public String getActionLink() {
        return getResources().createEventLink("skipTurn", hero).toAbsoluteURI();
    }
}
