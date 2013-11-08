/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components;

import com.mutabra.web.internal.CSSConstants;
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
                writer.element("div", "class", CSSConstants.ALERT + " " + CSSConstants.ALERT_BLOCK + " " + CSSConstants.ALERT_ERROR);
                for (String message : errors) {
                    writer.element("p");
                    writer.write(message);
                    writer.end();
                }
                writer.end();
            }
        }
    }
}
