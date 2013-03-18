package com.mutabra.web.components.game;

import com.mutabra.services.battle.BattleField;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
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
public class FieldPointDisplay extends AbstractComponent implements ClientElement {
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

    private String clientId;

    public String getClientId() {
        return clientId;
    }

    @BeginRender
    void begin(final MarkupWriter writer) {
        clientId = "f_" + point.getPosition().getId();
        final int startX = CELL_OUTER_SIZE * (3 * point.getPosition().getX());
        final int startY = CELL_OUTER_SIZE * (2 * point.getPosition().getY() + (point.getPosition().getX() + 1) % 2);

        final Element element = writer.element("g",
                "id", clientId,
                "transform", String.format("translate(%d, %d)", startX, startY),
                "data-position-x", String.valueOf(point.getPosition().getX()),
                "data-position-y", String.valueOf(point.getPosition().getY()))
                .addClassName(point.hasHero() ? "hero" : point.hasCreature() ? "creature" : "empty")
                .addClassName(point.isEnemySide() ? "enemy" : "friend");

        if (point.hasUnit()) {
            element.attributes(
                    "data-description-target", "#description_" + point.getPosition().getId(),
                    "data-field-target", "#actions_" + point.getPosition().getId());
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
