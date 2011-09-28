package com.mutabra.web.components;

import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRenderBody;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;
import org.apache.tapestry5.dom.Text;
import org.apache.tapestry5.func.Predicate;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Button {

	private static final String[][][] BUTTON_TYPES = {
			{
					{CSSConstants.BUTTON_TEXT_ONLY, CSSConstants.BUTTON_ICON_ONLY},
					{CSSConstants.BUTTON_ICON_ONLY, CSSConstants.BUTTON_ICONS_ONLY},
			},
			{
					{CSSConstants.BUTTON_TEXT_ONLY, CSSConstants.BUTTON_TEXT_ICON_SECONDARY},
					{CSSConstants.BUTTON_TEXT_ICON_PRIMARY, CSSConstants.BUTTON_TEXT_ICONS},
			},
	};

	@Parameter(defaultPrefix = BindingConstants.LITERAL, name = "class")
	private String className;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String primary;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String secondary;

	@Parameter
	private boolean selected;


	@AfterRenderBody
	void afterRenderBody(final MarkupWriter writer) {
		final Element button = writer.getElement().getElement(new Predicate<Element>() {
			public boolean accept(final Element element) {
				return element.getName().matches("^a|button|span");
			}
		});
		if (button != null) {
			if (button.getAttribute("role") == null) {
				if (className != null) {
					button.addClassName(className);
				}
				button.addClassName(
						CSSConstants.BUTTON,
						CSSConstants.WIDGET,
						CSSConstants.STATE_DEFAULT,
						CSSConstants.CORNER_ALL);
				if (selected) {
					button.addClassName(CSSConstants.STATE_HIGHLIGHT);
				}

				Element text = getText(button);
				final String buttonType = BUTTON_TYPES[text != null ? 1 : 0][primary != null ? 1 : 0][secondary != null ? 1 : 0];
				button.addClassName(buttonType);

				if (text == null) {
					text = button.element("span");
					text.text("");
				}
				text.addClassName(CSSConstants.BUTTON_TEXT);

				if (primary != null) {
					button.element("span")
							.addClassName(CSSConstants.BUTTON_ICON_PRIMARY, CSSConstants.ICON, primary)
							.moveBefore(text);
				}
				if (secondary != null) {
					button.element("span")
							.addClassName(CSSConstants.BUTTON_ICON_SECONDARY, CSSConstants.ICON, secondary)
							.moveAfter(text);
				}
				button.attribute("role", "button");
			}
		}
	}

	private Element getText(final Element element) {
		for (Node node : element.getChildren()) {
			if (node instanceof Text) {
				return node.wrap("span");
			}
		}
		return null;
	}
}
