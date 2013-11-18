/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.battle;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
import com.mutabra.domain.battle.BattlePosition;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.mixins.DiscardBody;
import org.bson.types.ObjectId;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleLogEntryDisplay extends AbstractComponent {
    private static final String PARAMETER_PREFIX = "{";
    private static final String PARAMETER_SUFIX = "}";

    @Parameter(required = true, allowNull = false)
    private BattleLogEntry value;

    @Parameter
    private BattleHero hero;

    @Mixin
    private DiscardBody discardBody;

    @BeginRender
    void begin(final MarkupWriter writer) {
        writer.element("p");

        final String message = translate(value);
        final Map<String, BattleLogParameter> parameters = value.getParameters();

        int index = 0;
        while (true) {
            final int start = message.indexOf(PARAMETER_PREFIX, index);
            // there are no more parameters
            if (start < 0) {
                writer.write(message.substring(index));
                break;
            }

            // append the part without parameters
            writer.write(message.substring(index, start));

            final int end = message.indexOf(PARAMETER_SUFIX, start);
            if (end < 0) {
                // close brace is missed
                throw new RuntimeException(
                        String.format("Close brace is missed in effect message '%s'.", message));
            }
            // calculate parameter
            final String parameterName = message.substring(start + PARAMETER_PREFIX.length(), end);
            final BattleLogParameter parameter = parameters.get(parameterName);
            if (parameter == null) {
                // parameter is missed
                throw new RuntimeException(
                        String.format("Parameter '%s' is missed for effect message '%s'.", parameterName, message));
            }
            writeParameter(writer, parameter);

            // restart the search after the '}'
            index = end + PARAMETER_SUFIX.length();
        }

        writer.end();
    }

    private void writeParameter(final MarkupWriter writer, final BattleLogParameter parameter) {
        if (parameter.getCreatureId() != null) {
            writeCreature(writer, parameter.getHeroId(), parameter.getCreatureId(), translate(parameter, Translatable.NAME));
        } else if (parameter.getCardId() != null) {
            writeCard(writer, parameter.getCardId(), translate(parameter, Translatable.NAME));
        } else if (parameter.getAbilityId() != null) {
            writeAbility(writer, parameter.getAbilityId(), translate(parameter, Translatable.NAME));
        } else if (parameter.getHeroId() != null) {
            writeHero(writer, parameter.getHeroId(), parameter.getValue());
        } else if (parameter.getCode() != null) {
            //TODO: do something with this
            writer.element("span", "class", "detail");
            writer.write("[" + translate(parameter) + "]");
            writer.end();
        } else if (parameter.getPosition() != null) {
            writePosition(writer, parameter.getPosition());
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

    private void writePosition(final MarkupWriter writer, final BattlePosition position) {
        writer.element("span",
                "class", "detail");
        writer.write("[" + position.getX() + "," + position.getY() + "]");
        writer.end();
    }

    private void writeRaw(final MarkupWriter writer, final String label) {
        writer.element("span",
                "class", "detail");
        writer.write(label);
        writer.end();
    }
}
