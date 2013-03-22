/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
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
 * @since 1.0
 */
@Import(stack = "mutabra")
@SupportsInformalParameters
public class EmptyLayout extends AbstractComponent {
    private static final String PAGE_TITLE_PROPERTY = "title";
    private static final String PAGE_SUBTITLE_PROPERTY = "subtitle";

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String subtitle;

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
    @Symbol(ApplicationConstants.ROBOT_EMAIL)
    private String robotEmail;

    @Inject
    private BindingSource bindingSource;

    Binding defaultTitle() {
        return bindingSource.newBinding("Page title", getResources().getContainerResources(), BindingConstants.PROP, PAGE_TITLE_PROPERTY);
    }

    Binding defaultSubtitle() {
        return bindingSource.newBinding("Page subtitle", getResources().getContainerResources(), BindingConstants.PROP, PAGE_SUBTITLE_PROPERTY);
    }
}
