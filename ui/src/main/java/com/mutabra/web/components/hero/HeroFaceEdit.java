/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractField;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Request;

import java.util.EnumSet;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class HeroFaceEdit extends AbstractField {

    @Parameter(required = true, allowNull = false)
    private HeroAppearance appearance;

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

    String defaultLabel() {
        return defaultProvider.defaultLabel(getResources());
    }

    public HeroAppearance getAppearance() {
        return appearance;
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
        final JSONObject submittedValue = InternalUtils.isNonBlank(submitted) ? new JSONObject(submitted) : null;

        // write hidden inputs
        for (HeroAppearancePart facePart : getParts()) {
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

    @CleanupRender
    void cleanup(final MarkupWriter writer) {
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
