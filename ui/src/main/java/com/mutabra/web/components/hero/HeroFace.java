/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractField;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Request;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroFace extends AbstractField {

    @Parameter(required = true, allowNull = false)
    private HeroAppearance appearance;

    @Parameter(value = "100")
    private int width;

    @Parameter(value = "100")
    private int height;

    @Parameter
    private boolean editable;

    private Iterator<HeroAppearancePart> iterator;

    @Environmental(false)
    private ValidationTracker tracker;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Inject
    private Request request;

    String defaultLabel() {
        return defaultProvider.defaultLabel(getResources());
    }

    public HeroAppearance getAppearance() {
        return appearance;
    }

    @Override
    protected boolean editable() {
        return editable;
    }

    @SetupRender
    void setup(final MarkupWriter writer) {
        // setup collection of parts that should be rendered
        final Set<HeroAppearancePart> faceParts = EnumSet.allOf(HeroAppearancePart.class);

        writer.element("div",
                "id", getClientId(),
                "class", "face-display");

        // assign control name
        if (editable()) {
            final String submitted = tracker.getInput(this);
            final JSONObject submittedValue = InternalUtils.isNonBlank(submitted) ? new JSONObject(submitted) : null;

            // write hidden inputs
            for (HeroAppearancePart facePart : faceParts) {
                final String value = submittedValue != null ?
                        submittedValue.getString(facePart.getCode()) :
                        getValue(facePart);

                writer.element("input",
                        "type", "hidden",
                        "id", getClientId() + "_" + facePart.getCode(),
                        "name", getControlName() + "_" + facePart.getCode(),
                        "data-part", facePart.getCode(),
                        "value", value);
                writer.end();
            }
        }

        writer.elementNS("http://www.w3.org/2000/svg", "svg").attributes(
                "width", String.valueOf(width),
                "height", String.valueOf(height),
                "version", "1.1");

        final double scaleX = (double) width / 100;
        final double scaleY = (double) height / 100;
        writer.element("g",
                "transform", String.format(Locale.US, "scale(%.3f,%.3f)", scaleX, scaleY));

        iterator = faceParts.iterator();
    }

    @BeginRender
    Object begin() {
        final HeroAppearancePart nextPart = iterator.next();
        final Block block = getResources().findBlock(nextPart.name().toLowerCase());
        return block != null ? block : Boolean.FALSE;
    }

    @AfterRender
    boolean end() {
        return !iterator.hasNext();
    }

    @CleanupRender
    void cleanup(final MarkupWriter writer) {
        writer.end(); // g
        writer.end(); // svg
        writer.end(); // div container
    }

    @Override
    protected void processSubmission(final String controlName) {
        final JSONObject submittedValue = new JSONObject();
        for (HeroAppearancePart facePart : HeroAppearancePart.values()) {
            final String submitted = request.getParameter(controlName + "_" + facePart.getCode());
            submittedValue.put(facePart.getCode(), submitted);

            if (submitted != null) {
                setValue(facePart, submitted);
            }
        }
        tracker.recordInput(this, submittedValue.toCompactString());

        // assign values
        for (HeroAppearancePart facePart : HeroAppearancePart.values()) {
            final String submitted = submittedValue.getString(facePart.getCode());

            if (submitted != null) {
                setValue(facePart, submitted);
            }
        }
    }

    private void setValue(final HeroAppearancePart facePart, final String value) {
        switch (facePart) {
            case NAME:
                appearance.setName(value);
                break;
            case RACE:
                appearance.setRace(value);
                break;
            case SEX:
                appearance.setSex(decode(Sex.class, value));
                break;
            case EARS:
                appearance.setEars(decode(Integer.class, value));
                break;
            case FACE:
                appearance.setFace(decode(Integer.class, value));
                break;
            case EYES:
                appearance.setEyes(decode(Integer.class, value));
                break;
            case EYEBROWS:
                appearance.setEyebrows(decode(Integer.class, value));
                break;
            case NOSE:
                appearance.setNose(decode(Integer.class, value));
                break;
            case MOUTH:
                appearance.setMouth(decode(Integer.class, value));
                break;
            case HAIR:
                appearance.setHair(decode(Integer.class, value));
                break;
            case FACIAL_HAIR:
                appearance.setFacialHair(decode(Integer.class, value));
                break;
        }
    }

    private String getValue(final HeroAppearancePart facePart) {
        switch (facePart) {
            case NAME:
                return appearance.getName();
            case RACE:
                return appearance.getRace();
            case SEX:
                return encode(Sex.class, appearance.getSex());
            case EARS:
                return String.valueOf(appearance.getEars());
            case FACE:
                return String.valueOf(appearance.getFace());
            case EYES:
                return String.valueOf(appearance.getEyes());
            case EYEBROWS:
                return String.valueOf(appearance.getEyebrows());
            case NOSE:
                return String.valueOf(appearance.getNose());
            case MOUTH:
                return String.valueOf(appearance.getMouth());
            case HAIR:
                return String.valueOf(appearance.getHair());
            case FACIAL_HAIR:
                return String.valueOf(appearance.getFacialHair());
            default:
                return null;
        }
    }
}
