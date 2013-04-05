package com.mutabra.web.components.battle;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.battle.*;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.mixins.DiscardBody;
import org.bson.types.ObjectId;

import java.util.Iterator;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleLogEntryDisplay extends AbstractComponent {
    private static final String PARAMETER_PLACEHOLDER = "{}";

    @Parameter(required = true, allowNull = false)
    private BattleLogEntry value;

    @Parameter
    private BattleHero hero;

    @Mixin
    private DiscardBody discardBody;

    @BeginRender
    void begin(final MarkupWriter writer) {
        writer.element("p");

        final String message = label(value);
        final Iterator<BattleLogParameter> iterator = value.getParameters().iterator();

        int start = 0;
        int index = message.indexOf(PARAMETER_PLACEHOLDER, start);
        while (index >= 0) {
            if (index > start) {
                writer.write(message.substring(start, index));
            }
            if (iterator.hasNext()) {
                writeParameter(writer, iterator.next());
            } else {
                // error
                writer.write("_null_");
            }

            start = index + PARAMETER_PLACEHOLDER.length();
            index = message.indexOf(PARAMETER_PLACEHOLDER, start);
        }
        // process tail
        if (start < message.length()) {
            writer.write(message.substring(start));
        }

        writer.end();
    }

    private void writeParameter(final MarkupWriter writer, final BattleLogParameter parameter) {
        if (parameter.getCreatureId() != null) {
            writeCreature(writer, parameter.getHeroId(), parameter.getCreatureId(), label(parameter, Translatable.NAME));
        } else if (parameter.getCardId() != null) {
            writeCard(writer, parameter.getCardId(), label(parameter, Translatable.NAME));
        } else if (parameter.getAbilityId() != null) {
            writeAbility(writer, parameter.getAbilityId(), label(parameter, Translatable.NAME));
        } else if (parameter.getHeroId() != null) {
            writeHero(writer, parameter.getHeroId(), parameter.getValue());
        } else if (parameter.getPosition() != null && parameter.getSide() != null) {
            writePosition(writer, parameter.getPosition(), parameter.getSide());
        } else {
            writeRaw(writer, parameter.getValue());
        }
    }

    private void writeHero(final MarkupWriter writer, final ObjectId heroId, final String label) {
        final String description = "#" + BattleHeroDescription.ID_PREFIX + encode(ObjectId.class, heroId);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", hero.getId().equals(heroId) ? "friend" : "enemy");
        writer.write("[" + label + "]");
        writer.end();
    }

    private void writeCreature(final MarkupWriter writer, final ObjectId heroId, final Long creatureId, final String label) {
        final String description = "#" + BattleCreatureDescription.ID_PREFIX + encode(Long.class, creatureId);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", hero.getId().equals(heroId) ? "friend" : "enemy");
        writer.write("[" + label + "]");
        writer.end();
    }

    private void writeCard(final MarkupWriter writer, final Long cardId, final String label) {
        final String description = "#" + BattleCardDescription.ID_PREFIX + encode(Long.class, cardId);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", "detail");
        writer.write("[" + label + "]");
        writer.end();
    }

    private void writeAbility(final MarkupWriter writer, final Long abilityId, final String label) {
        final String description = "#" + BattleAbilityDescription.ID_PREFIX + encode(Long.class, abilityId);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", "detail");
        writer.write("[" + label + "]");
        writer.end();
    }

    private void writePosition(final MarkupWriter writer, final BattlePosition position, final BattleSide side) {
        writer.element("span",
                "class", "detail");
        writer.write("[" + position.getX() + "," + position.getY() + "]");
        writer.end();
    }

    private void writeRaw(final MarkupWriter writer, final String label) {
        writer.element("span",
                "class", "detail");
        writer.write("[" + label + "]");
        writer.end();
    }
}
