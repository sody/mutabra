package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleField;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
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
	private static final int CELL_SIZE = 40;
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
	void render(final MarkupWriter writer) {
		clientId = "f_" + field.getPosition().getId();
		final int startX = CELL_SIZE * (3 * field.getPosition().getX() + 1);
		final int startY = CELL_SIZE * (2 * field.getPosition().getY() + 2 + (field.getPosition().getX() + 1) % 2);

		final Element path = writer.element("path", "stroke", "#333", "fill", "transparent");
		path.attribute("id", clientId);
		if (field.hasUnit()) {
			path.attribute("data-hover", "description");
			path.attribute("data-description-target", "#description_" + field.getPosition().getId());
			path.attribute("data-select", "field");
			path.attribute("data-field-target", "#actions_" + field.getPosition().getId());
		}
		path.attribute("data-position-x", String.valueOf(field.getPosition().getX()));
		path.attribute("data-position-y", String.valueOf(field.getPosition().getY()));

		path.addClassName(field.hasHero() ? "hero" : field.hasCreature() ? "creature" : "empty");
		path.addClassName(field.isEnemySide() ? "enemy" : "friend");
		if (field.hasHero() && !field.isEnemySide()) {
			path.addClassName("active");
		}

		final StringBuilder pathBuilder = new StringBuilder("m");
		pathBuilder.append(startX).append(',').append(startY);
		for (int[] point : CELL_PATH) {
			pathBuilder.append(',').append(point[0] * CELL_SIZE).append(',').append(point[1] * CELL_SIZE);
		}
		pathBuilder.append("z");
		path.attribute("d", pathBuilder.toString());

		writer.end();
	}
}
