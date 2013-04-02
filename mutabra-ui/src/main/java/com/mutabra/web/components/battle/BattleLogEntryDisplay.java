package com.mutabra.web.components.battle;

import com.mutabra.domain.battle.*;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.mixins.DiscardBody;

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

        final String message = message(value.getBasename() + "." + value.getCode());
        final Iterator<BattleTarget> iterator = value.getParameters().iterator();

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

    private void writeParameter(final MarkupWriter writer, final BattleTarget battleTarget) {
        if (battleTarget.getHero() != null) {
            writeHero(writer, battleTarget.getHero());
        } else if (battleTarget.getCreature() != null) {
            writeCreature(writer, battleTarget.getCreature());
        } else if (battleTarget.getCard() != null) {
            writeCard(writer, battleTarget.getCard());
        } else if (battleTarget.getAbility() != null) {
            writeAbility(writer, battleTarget.getAbility());
        } else if (battleTarget.getPosition() != null && battleTarget.getSide() != null) {
            writePosition(writer, battleTarget.getPosition(), battleTarget.getSide());
        } else {
            // error
            writer.write("_null_");
        }
    }

    private void writeHero(final MarkupWriter writer, final BattleHero battleHero) {
        final String description = "#" + BattleHeroDescription.ID_PREFIX + encode(BattleHero.class, battleHero);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", hero.equals(battleHero) ? "friend" : "enemy");
        writer.write("[" + battleHero.getAppearance().getName() + "]");
        writer.end();
    }

    private void writeCard(final MarkupWriter writer, final BattleCard battleCard) {
        final String description = "#" + BattleCardDescription.ID_PREFIX + encode(BattleCard.class, battleCard);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", "detail");
        writer.write("[" + label(battleCard, BattleCard.NAME) + "]");
        writer.end();
    }

    private void writeCreature(final MarkupWriter writer, final BattleCreature battleCreature) {
        final String description = "#" + BattleCreatureDescription.ID_PREFIX + encode(BattleCreature.class, battleCreature);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", hero.equals(battleCreature.getHero()) ? "friend" : "enemy");
        writer.write("[" + label(battleCreature, BattleCreature.NAME) + "]");
        writer.end();
    }

    private void writeAbility(final MarkupWriter writer, final BattleAbility battleAbility) {
        final String description = "#" + BattleAbilityDescription.ID_PREFIX + encode(BattleAbility.class, battleAbility);
        writer.element("a",
                "href", description,
                "data-description-target", description,
                "class", "detail");
        writer.write("[" + label(battleAbility, BattleAbility.NAME) + "]");
        writer.end();
    }

    private void writePosition(final MarkupWriter writer, final BattlePosition position, final BattleSide side) {
        writer.element("span",
                "class", "detail");
        writer.write("[" + position.getX() + "," + position.getY() + "]");
        writer.end();
    }
}
