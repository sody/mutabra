/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.form;

import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.internal.InternalMessages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class Errors {

    @Parameter
    private List<String> errors;

    @Inject
    private ComponentResources resources;

    @Environmental(false)
    private ValidationTracker tracker;

    @BeginRender
    void beginRender(final MarkupWriter writer) {
        final List<String> errors = getErrors();

        if (errors != null && !errors.isEmpty()) {
            writer.element("div", "class", CSSConstants.ALERT + " " + CSSConstants.ALERT_BLOCK + " " + CSSConstants.ALERT_ERROR);
            for (String message : errors) {
                writer.element("p");
                writer.write(message);
                writer.end();
            }
            writer.end();
        }
    }

    private List<String> getErrors() {
        if (resources.isBound("errors")) {
            return errors;
        }

        if (tracker == null) {
            throw new RuntimeException(InternalMessages.encloseErrorsInForm());
        }

        return tracker.getErrors();
    }
}
