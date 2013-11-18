/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.layout;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 */
public class DefaultLayout extends EmptyLayout {

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String header;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String headerNote;

    Binding defaultHeader() {
        return pageProperty("header");
    }

    Binding defaultHeaderNote() {
        return pageProperty("headerNote");
    }
}
