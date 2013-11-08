/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.ValidationDecoratorFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CustomValidationDecoratorFactory implements ValidationDecoratorFactory {
    private final Environment environment;

    public CustomValidationDecoratorFactory(final Environment environment) {
        this.environment = environment;
    }

    public ValidationDecorator newInstance(final MarkupWriter writer) {
        return new CustomValidationDecorator(environment, writer);
    }
}
