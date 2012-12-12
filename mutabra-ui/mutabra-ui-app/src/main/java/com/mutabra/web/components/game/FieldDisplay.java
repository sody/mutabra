package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleField;
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
public class FieldDisplay extends AbstractComponent implements ClientElement {
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
    private BattleField field;

    private String clientId;

    public String getClientId() {
        return clientId;
    }

    @BeginRender
    void begin(final MarkupWriter writer) {
        clientId = "f_" + field.getPosition().getId();
        final int startX = CELL_OUTER_SIZE * (3 * field.getPosition().getX());
        final int startY = CELL_OUTER_SIZE * (2 * field.getPosition().getY() + (field.getPosition().getX() + 1) % 2);

        final Element element = writer.element("g",
                "id", clientId,
                "transform", String.format("translate(%d, %d)", startX, startY),
                "data-position-x", String.valueOf(field.getPosition().getX()),
                "data-position-y", String.valueOf(field.getPosition().getY()))
                .addClassName(field.hasHero() ? "hero" : field.hasCreature() ? "creature" : "empty")
                .addClassName(field.isEnemySide() ? "enemy" : "friend");

        if (field.hasUnit()) {
            element.attributes(
                    "data-description-target", "#description_" + field.getPosition().getId(),
                    "data-field-target", "#actions_" + field.getPosition().getId());
        }
        if (field.hasHero() && !field.isEnemySide()) {
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
