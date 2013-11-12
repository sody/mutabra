package com.mutabra.web.components.form;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.HeartbeatDeferred;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class FormRow {

    @Parameter(name = "for", defaultPrefix = BindingConstants.COMPONENT)
    private Field field;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String fieldClass;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String label;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String labelClass;

    @Parameter
    private boolean required;

    private Element containerElement;
    private Element labelElement;

    @Inject
    private ComponentResources resources;

    @Environmental
    private ValidationDecorator decorator;

    @Environmental(false)
    private ValidationTracker tracker;

    @BeginRender
    void begin(final MarkupWriter writer) {
        // open container
        containerElement = writer.element("div");
        resources.renderInformalParameters(writer);
        containerElement.addClassName("form-group");

        // label
        final String label = getLabel();
        if (label != null) {
            decorator.beforeLabel(field);

            labelElement = writer.element("label",
                    "class", labelClass)
                    .addClassName("control-label");
            if (isRequired()) {
                labelElement.addClassName("required");
            }
            writer.write(label + ":");
            if (field != null) {
                updateLabelFromField();
            }
            decorator.insideLabel(field, labelElement);
            writer.end();
            decorator.afterLabel(field);
        }

        // open field
        final Element fieldContainer = writer.element("div");
        if (fieldClass != null) {
            fieldContainer.addClassName(fieldClass);
        }
    }

    @AfterRender
    void end(final MarkupWriter writer) {
        // mark as in error
        if (inError()) {
            containerElement.addClassName("has-error");
        }

        // close field
        writer.end();
        // close container
        writer.end();
    }

    @HeartbeatDeferred
    private void updateLabelFromField() {
        labelElement.forceAttributes("for", field.getClientId());
    }

    private boolean inError() {
        return field != null && tracker != null && tracker.inError(field);
    }

    private boolean isRequired() {
        if (resources.isBound("required")) {
            // if required parameter is bound
            // it has higher priority than field
            return required;
        } else {
            return field != null && field.isRequired();
        }
    }

    private String getLabel() {
        if (resources.isBound("label")) {
            // if label parameter is bound
            // it has higher priority than field
            return label;
        } else {
            return field != null ? field.getLabel() : null;
        }
    }
}
