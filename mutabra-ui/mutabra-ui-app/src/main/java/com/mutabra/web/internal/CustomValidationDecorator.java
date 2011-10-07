package com.mutabra.web.internal;

import org.apache.tapestry5.*;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.Environment;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CustomValidationDecorator extends BaseValidationDecorator {

	private final Environment environment;

	private final MarkupWriter writer;

	public CustomValidationDecorator(final Environment environment, final MarkupWriter writer) {
		this.environment = environment;
		this.writer = writer;
	}

	@Override
	public void insideLabel(final Field field, final Element labelElement) {
		if (field != null && inError(field)) {
			addErrorClass(labelElement);
		}
	}

	@Override
	public void afterLabel(final Field field) {
		if (field != null && field.isRequired()) {
			writer.getElement().element("span").addClassName("required").text("*");

		}
	}

	@Override
	public void insideField(Field field) {
		if (inError(field)) {
			addErrorClass(writer.getElement());
		}
	}

	private void addErrorClass(final Element element) {
		element.addClassName(CSSClassConstants.ERROR);
	}

	private boolean inError(final Field field) {
		return environment.peekRequired(ValidationTracker.class).inError(field);
	}
}
