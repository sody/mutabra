/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractClientElement;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Request;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroFace extends AbstractClientElement implements Field {
    private static final ComponentAction<HeroFace> PROCESS_SUBMISSION_ACTION = new ProcessSubmission();

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String label;

    @Parameter(required = true, allowNull = false)
    private HeroAppearance appearance;

    @Parameter
    private boolean editable;

    private String controlName;
    private Iterator<HeroAppearancePart> iterator;

    @Environmental(false)
    private FormSupport formSupport;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Inject
    private Request request;

    String defaultLabel() {
        return defaultProvider.defaultLabel(getResources());
    }

    @Override
    public String getControlName() {
        return controlName;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    public HeroAppearance getAppearance() {
        return appearance;
    }

    @SetupRender
    void setup(final MarkupWriter writer) {
        // setup collection of parts that should be rendered
        final Set<HeroAppearancePart> faceParts = EnumSet.allOf(HeroAppearancePart.class);

        writer.element("div",
                "id", getClientId(),
                "class", "face-display");

        // assign control name
        if (editable) {
            // should be inside form
            if (formSupport == null) {
                throw new RuntimeException(String.format("Component %s must be enclosed by a Form component.",
                        getResources().getCompleteId()));
            }

            controlName = getResources().isBound("id") ?
                    getClientId() :
                    formSupport.allocateControlName(getResources().getId());

            formSupport.storeAndExecute(this, new Setup(controlName));
            formSupport.store(this, PROCESS_SUBMISSION_ACTION);

            // write hidden inputs
            for (HeroAppearancePart facePart : faceParts) {
                final String facePartValue;
                switch (facePart) {
                    case NAME:
                        facePartValue = appearance.getName();
                        break;
                    case RACE:
                        facePartValue = appearance.getRace();
                        break;
                    case SEX:
                        facePartValue = encode(Sex.class, appearance.getSex());
                        break;
                    case EARS:
                        facePartValue = String.valueOf(appearance.getEars());
                        break;
                    case FACE:
                        facePartValue = String.valueOf(appearance.getFace());
                        break;
                    case EYES:
                        facePartValue = String.valueOf(appearance.getEyes());
                        break;
                    case EYEBROWS:
                        facePartValue = String.valueOf(appearance.getEyebrows());
                        break;
                    case NOSE:
                        facePartValue = String.valueOf(appearance.getNose());
                        break;
                    case MOUTH:
                        facePartValue = String.valueOf(appearance.getMouth());
                        break;
                    case HAIR:
                        facePartValue = String.valueOf(appearance.getHair());
                        break;
                    case FACIAL_HAIR:
                        facePartValue = String.valueOf(appearance.getFacialHair());
                        break;
                    default:
                        facePartValue = null;
                }
                final String facePartSufix = "_" + facePart.name().toLowerCase();
                writer.element("input",
                        "type", "hidden",
                        "id", getClientId() + facePartSufix,
                        "name", getControlName() + facePartSufix,
                        "value", facePartValue);
                writer.end();
            }
        }

        writer.elementNS("http://www.w3.org/2000/svg", "svg").attributes(
                "width", "350",
                "height", "350",
                "version", "1.1");

        writer.element("g",
                "transform", "scale(3.500,3.500)");

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

    private void setupControlName(final String controlName) {
        this.controlName = controlName;
    }

    private void processSubmission() {
        for (HeroAppearancePart facePart : HeroAppearancePart.values()) {
            final String submitted = request.getParameter(getControlName() + "_" + facePart.name().toLowerCase());
            if (submitted != null) {
                switch (facePart) {
                    case NAME:
                        appearance.setName(submitted);
                        break;
                    case RACE:
                        appearance.setRace(submitted);
                        break;
                    case SEX:
                        appearance.setSex(decode(Sex.class, submitted));
                        break;
                    case EARS:
                        appearance.setEars(decode(Integer.class, submitted));
                        break;
                    case FACE:
                        appearance.setFace(decode(Integer.class, submitted));
                        break;
                    case EYES:
                        appearance.setEyes(decode(Integer.class, submitted));
                        break;
                    case EYEBROWS:
                        appearance.setEyebrows(decode(Integer.class, submitted));
                        break;
                    case NOSE:
                        appearance.setNose(decode(Integer.class, submitted));
                        break;
                    case MOUTH:
                        appearance.setMouth(decode(Integer.class, submitted));
                        break;
                    case HAIR:
                        appearance.setHair(decode(Integer.class, submitted));
                        break;
                    case FACIAL_HAIR:
                        appearance.setFacialHair(decode(Integer.class, submitted));
                        break;
                }
            }
        }
    }

    static class Setup implements ComponentAction<HeroFace>, Serializable {
        private static final long serialVersionUID = 2690270608212092340L;

        private final String controlName;

        public Setup(final String controlName) {
            this.controlName = controlName;
        }

        public void execute(final HeroFace component) {
            component.setupControlName(controlName);
        }

        @Override
        public String toString() {
            return String.format("AbstractField.Setup[%s]", controlName);
        }
    }

    static class ProcessSubmission implements ComponentAction<HeroFace>, Serializable {
        private static final long serialVersionUID = -4346676414137434418L;

        public void execute(final HeroFace component) {
            component.processSubmission();
        }

        @Override
        public String toString() {
            return "AbstractField.ProcessSubmission";
        }
    }
}
