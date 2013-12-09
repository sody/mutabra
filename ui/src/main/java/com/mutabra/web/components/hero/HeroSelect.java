/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractField;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Request;

import java.util.EnumSet;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
@Import(library = "context:/mutabra/js/face-generator.js")
public class HeroSelect extends AbstractField {

    @Parameter(required = true, allowNull = false)
    private Hero value;

    @Parameter
    private ValueEncoder<Hero> encoder;

    @Parameter(value = "350")
    private int width;

    @Parameter(value = "350")
    private int height;

    @Environmental(false)
    private ValidationTracker tracker;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Inject
    private Request request;

    ValueEncoder defaultEncoder() {
        return defaultProvider.defaultValueEncoder("value", getResources());
    }

    public HeroAppearance getAppearance() {
        return value.getAppearance();
    }

    public Iterable<HeroAppearancePart> getParts() {
        return EnumSet.allOf(HeroAppearancePart.class);
    }

    @SetupRender
    void setup(final MarkupWriter writer) {
        // render container element
        writer.element("div",
                "id", getClientId(),
                "class", "face-editor");

        getResources().renderInformalParameters(writer);

        // assign control name
        final String submitted = tracker.getInput(this);
        final String value = submitted != null ? submitted : encoder.toClient(this.value);

        // write hidden input
        writer.element("input",
                "type", "hidden",
                "id", getClientId() + "_hero",
                "name", getControlName(),
                "data-part", "all",
                "value", value);
        writer.end();
    }

    @CleanupRender
    void cleanup(final MarkupWriter writer) {
        writer.end(); // div container
    }

    @Override
    protected void processSubmission(final String controlName) {
        final String submitted = request.getParameter(controlName);
        tracker.recordInput(this, submitted);

        value = encoder.toValue(submitted);
    }
}
