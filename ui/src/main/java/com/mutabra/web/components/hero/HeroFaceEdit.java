/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.services.game.Names;
import com.mutabra.web.base.components.AbstractField;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.util.ExceptionUtils;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

import java.util.EnumSet;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
@Import(library = "context:/mutabra/js/face-generator.js")
public class HeroFaceEdit extends AbstractField {

    @Parameter(required = true, allowNull = false)
    private HeroAppearance appearance;

    @Parameter(value = "350")
    private int width;

    @Parameter(value = "350")
    private int height;

    @Parameter(value = "50")
    private int nameCount;

    @Environmental(false)
    private ValidationTracker tracker;

    @Inject
    private PropertyAccess propertyAccess;

    @Inject
    private Request request;

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
                "class", "face-editor",
                "data-toggle", "face-generator");

        getResources().renderInformalParameters(writer);

        // assign control name
        final String submitted = tracker.getInput(this);
        final JSONObject submittedValue = InternalUtils.isNonBlank(submitted) ? new JSONObject(submitted) : null;

        // write hidden inputs
        for (HeroAppearancePart facePart : getParts()) {
            final String value = submittedValue != null ?
                    submittedValue.getString(facePart.getCode()) :
                    toClient(facePart, getValue(facePart));

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

        final JSONArray names = new JSONArray();
        for (int i = 0; i < nameCount; i++) {
            names.put(Names.generate());
        }
        getJavaScriptSupport().addScript("jQuery('#%s').faceGenerator(%s)", getClientId(), new JSONObject()
                .put("names", names));
    }

    @Override
    protected void processSubmission(final String controlName) {
        final JSONObject submittedValue = new JSONObject();
        for (HeroAppearancePart facePart : HeroAppearancePart.values()) {
            final String submitted = request.getParameter(controlName + "_" + facePart.getCode());
            submittedValue.put(facePart.getCode(), submitted);
        }
        tracker.recordInput(this, submittedValue.toCompactString());

        // assign values
        for (HeroAppearancePart facePart : HeroAppearancePart.values()) {
            final String submitted = submittedValue.getString(facePart.getCode());

            if (submitted != null) {
                try {
                    final Object selected = InternalUtils.isBlank(submitted) ? null : toValue(facePart, submitted);
                    // validate submitted value
                    getResources().triggerEvent(EventConstants.VALIDATE, new Object[]{facePart, selected}, null);
                    setValue(facePart, selected);
                } catch (ValidationException ex) {
                    tracker.recordError(ex.getMessage());
                } catch (RuntimeException ex) {
                    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
                    final ValidationException ve = ExceptionUtils.findCause(ex, ValidationException.class, propertyAccess);
                    if (ve == null) {
                        throw ex;
                    }
                    tracker.recordError(ve.getMessage());
                }
            }
        }
    }

    private Object toValue(final HeroAppearancePart part, final String client) throws ValidationException {
        try {
            switch (part) {
                case NAME:
                case RACE:
                    return client;
                case SEX:
                    return decode(Sex.class, client);
                default:
                    return decode(Integer.class, client);
            }
        } catch (RuntimeException ex) {
            throw new ValidationException(format("error.wrong-part", translate(part)));
        }
    }

    private String toClient(final HeroAppearancePart part, final Object value) {
        switch (part) {
            case NAME:
            case RACE:
                return (String) value;
            case SEX:
                return encode(Sex.class, (Sex) value);
            default:
                return encode(Integer.class, (Integer) value);
        }
    }

    private void setValue(final HeroAppearancePart facePart, final Object value) {
        switch (facePart) {
            case NAME:
                appearance.setName((String) value);
                break;
            case RACE:
                appearance.setRace((String) value);
                break;
            case SEX:
                appearance.setSex((Sex) value);
                break;
            case EARS:
                appearance.setEars((Integer) value);
                break;
            case FACE:
                appearance.setFace((Integer) value);
                break;
            case EYES:
                appearance.setEyes((Integer) value);
                break;
            case EYEBROWS:
                appearance.setEyebrows((Integer) value);
                break;
            case NOSE:
                appearance.setNose((Integer) value);
                break;
            case MOUTH:
                appearance.setMouth((Integer) value);
                break;
            case HAIR:
                appearance.setHair((Integer) value);
                break;
            case FACIAL_HAIR:
                appearance.setFacialHair((Integer) value);
                break;
        }
    }

    private Object getValue(final HeroAppearancePart facePart) {
        switch (facePart) {
            case NAME:
                return appearance.getName();
            case RACE:
                return appearance.getRace();
            case SEX:
                return appearance.getSex();
            case EARS:
                return appearance.getEars();
            case FACE:
                return appearance.getFace();
            case EYES:
                return appearance.getEyes();
            case EYEBROWS:
                return appearance.getEyebrows();
            case NOSE:
                return appearance.getNose();
            case MOUTH:
                return appearance.getMouth();
            case HAIR:
                return appearance.getHair();
            case FACIAL_HAIR:
                return appearance.getFacialHair();
            default:
                return null;
        }
    }
}
