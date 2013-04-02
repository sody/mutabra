package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleSide;
import com.mutabra.services.battle.BattleField;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class BattleFieldPointDisplay extends AbstractComponent {
    private static final int CELL_SIZE = 45;
    private static final int CELL_OUTER_SIZE = 50;
    private static final int[][] CELL_PATH = {
            {2, 0},
            {1, 1},
            {-1, 1},
            {-2, 0},
            {-1, -1},
            {1, -1},
    };

    @Parameter(required = true, allowNull = false)
    private BattleField.Point point;

    @BeginRender
    void begin(final MarkupWriter writer) {
        final int x = point.getPosition().getX();
        final int y = point.getPosition().getY() + (point.getSide() == BattleSide.YOUR ? 1 + x % 2 : 0);
        final int startX = CELL_OUTER_SIZE * (3 * x);
        final int startY = CELL_OUTER_SIZE * (2 * y + (x + 1) % 2);

        final Element element = writer.element("g",
                "transform", String.format("translate(%d, %d)", startX, startY),
                "data-position-x", String.valueOf(point.getPosition().getX()),
                "data-position-y", String.valueOf(point.getPosition().getY()),
                "data-side", String.valueOf(point.getSide()))
                .addClassName(point.hasHero() ? "hero" : point.hasCreature() ? "creature" : "empty")
                .addClassName(point.isEnemySide() ? "enemy" : "friend");

        if (point.hasUnit()) {
            final String unitValue = point.hasHero() ?
                    encode(BattleHero.class, point.getHero()) :
                    encode(BattleCreature.class, point.getCreature());
            final String descriptionSelector = point.hasHero() ?
                    "#" + BattleHeroDescription.ID_PREFIX + unitValue :
                    "#" + BattleCreatureDescription.ID_PREFIX + unitValue;
            final String handSelector = point.hasHero() ?
                    "#" + BattleHeroHandDisplay.ID_PREFIX + unitValue :
                    "#" + BattleCreatureHandDisplay.ID_PREFIX + unitValue;

            element.attribute("data-description-target", descriptionSelector);
            element.attribute("data-field-target", handSelector);
        }
        if (point.hasHero() && !point.isEnemySide()) {
            element.addClassName("active");
        }

        final StringBuilder pathBuilder = new StringBuilder("m");
        pathBuilder.append(CELL_OUTER_SIZE).append(',').append(CELL_OUTER_SIZE - CELL_SIZE);
        for (int[] point : CELL_PATH) {
            pathBuilder.append(',').append(point[0] * CELL_SIZE).append(',').append(point[1] * CELL_SIZE);
        }
        pathBuilder.append("z");
        writer.element("path",
                "d", pathBuilder.toString());
        writer.end();
    }

    @AfterRender
    void end(final MarkupWriter writer) {
        writer.end();
    }
}
