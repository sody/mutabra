/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.layout;

import com.mutabra.web.ApplicationConstants;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BindingSource;

/**
 * @author Ivan Khalopik
 */
@Import(stack = "mutabra")
@SupportsInformalParameters
public class EmptyLayout extends AbstractComponent {

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String applicationVersion;

    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @Property
    @Inject
    @Symbol(ApplicationConstants.SUPPORT_EMAIL)
    private String supportEmail;

    @Inject
    private BindingSource bindingSource;

    Binding defaultTitle() {
        return pageProperty("title");
    }

    protected Binding pageProperty(final String property) {
        return bindingSource.newBinding("Page " + property, getResources().getPage().getComponentResources(), BindingConstants.PROP, property);
    }
}
