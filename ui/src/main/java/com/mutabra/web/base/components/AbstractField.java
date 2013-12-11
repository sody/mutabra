/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.base.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FormSupport;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractField extends AbstractClientElement implements Field {
    private static final ComponentAction<AbstractField> PROCESS_SUBMISSION_ACTION = new ProcessSubmission();

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String label;

    @Parameter("false")
    private boolean disabled;

    private String controlName;

    @Environmental(false)
    private FormSupport formSupport;

    @Environmental(false)
    private ValidationDecorator decorator;

    @Inject
    private ComponentDefaultProvider defaultProvider;

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
        return disabled;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @SetupRender
    protected void setup() {
        // it will have field behaviour only inside form
        if (editable()) {
            // assign control name
            controlName = getResources().isBound("id") ?
                    getClientId() :
                    formSupport.allocateControlName(getResources().getId());

            // store form actions
            formSupport.storeAndExecute(this, new Setup(controlName));
            formSupport.store(this, PROCESS_SUBMISSION_ACTION);
        }
    }

    @BeginRender
    void beforeDecorator() {
        if (editable()) {
            decorator.beforeField(this);
        }
    }

    /**
     * Allows the validation decorator to write markup after the field has written all of its markup.
     */
    @AfterRender
    void afterDecorator() {
        if (editable()) {
            decorator.afterField(this);
        }
    }

    protected boolean editable() {
        return formSupport != null;
    }

    protected abstract void processSubmission(final String controlName);

    private void setupControlName(final String controlName) {
        this.controlName = controlName;
    }

    private void processSubmission() {
        if (!disabled) {
            processSubmission(controlName);
        }
    }

    static class Setup implements ComponentAction<AbstractField>, Serializable {
        private static final long serialVersionUID = 2690270808212092340L;

        private final String controlName;

        public Setup(final String controlName) {
            this.controlName = controlName;
        }

        public void execute(final AbstractField component) {
            component.setupControlName(controlName);
        }

        @Override
        public String toString() {
            return String.format("AbstractField.Setup[%s]", controlName);
        }
    }

    static class ProcessSubmission implements ComponentAction<AbstractField>, Serializable {
        private static final long serialVersionUID = -4346426414137434418L;

        public void execute(final AbstractField component) {
            component.processSubmission();
        }

        @Override
        public String toString() {
            return "AbstractField.ProcessSubmission";
        }
    }
}