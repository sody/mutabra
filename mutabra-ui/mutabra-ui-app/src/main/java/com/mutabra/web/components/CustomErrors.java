package com.mutabra.web.components;

import com.mutabra.web.internal.CSSConstants;
import com.mutabra.web.internal.CSSConstantsEx;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.corelib.internal.InternalMessages;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CustomErrors {

	@Environmental(false)
	private ValidationTracker tracker;

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		if (tracker == null) {
			throw new RuntimeException(InternalMessages.encloseErrorsInForm());
		}

		if (tracker.getHasErrors()) {
			final List<String> errors = tracker.getErrors();
			if (!errors.isEmpty()) {
				writer.element("div", "class", "control-group");
				for (String message : errors) {
					writer.element("div").addClassName(CSSConstants.STATE_ERROR, CSSConstants.CORNER_ALL);
					writer.element("p");
					writer.element("span", "style", "float: left; margin-right: 5px;")
							.addClassName(CSSConstants.ICON, "ui-icon-alert");
					writer.end();
					writer.write(message);
					writer.end();
					writer.end();
				}
				writer.end();
			}
		}
	}
}
