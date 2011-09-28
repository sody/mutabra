package com.mutabra.web.components;

import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.func.Predicate;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Button {

	@Parameter(defaultPrefix = BindingConstants.LITERAL, name = "class")
	private String className;

	@Parameter
	private boolean selected;

	@AfterRender
	void renderClass(final MarkupWriter writer) {
		final Element menuItem = writer.getElement().getElement(new Predicate<Element>() {
			public boolean accept(final Element element) {
				return "a".equalsIgnoreCase(element.getName()) || "span".equalsIgnoreCase(element.getName());
			}
		});
		if (menuItem != null) {
			if (className != null) {
				menuItem.addClassName(className);
			}
			menuItem.addClassName(
					CSSConstants.BUTTON,
					CSSConstants.BUTTON_TEXT_ONLY,
					CSSConstants.WIDGET,
					CSSConstants.STATE_DEFAULT,
					CSSConstants.CORNER_ALL);
			if (selected) {
				menuItem.addClassName(CSSConstants.STATE_HIGHLIGHT);
			}
		}
	}
}
