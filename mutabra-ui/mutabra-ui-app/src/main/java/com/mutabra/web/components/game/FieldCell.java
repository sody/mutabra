package com.mutabra.web.components.game;

import com.mutabra.web.base.components.AbstractComponent;
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
public class FieldCell extends AbstractComponent {
	private static final int CELL_SIZE = 40;
	private static final int[][] CELL_PATH = {
			{2, 0},
			{1, 1},
			{-1, 1},
			{-2, 0},
			{-1, -1},
			{1, -1},
	};

	@Parameter(required = true)
	private int x;

	@Parameter(required = true)
	private int y;

	@Parameter
	private boolean selected;

	@Parameter(value = "true")
	private boolean empty;

	@BeginRender
	void render(final MarkupWriter writer) {
		final int startX = CELL_SIZE * (3 * x + 1);
		final int startY = CELL_SIZE * (2 * y + 2 + (x + 1) % 2);

		final Element path = writer.element("path", "stroke", "#333", "fill", "transparent");
		path.attribute("id", "f_" + (x * 100 + y));
		if (!empty) {
			path.addClassName("busy");
		}
		if (selected) {
			path.addClassName("selected");
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
