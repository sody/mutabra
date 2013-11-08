/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.MixinClasses;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.mixins.RenderInformals;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Dropdown extends AbstractComponent implements ClientElement {

    @Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String label;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String[] items;

    @Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
    private String className;

    @Property
    @Parameter(value = "this", allowNull = false)
    private PropertyOverrides overrides;

    @Component(inheritInformalParameters = true)
    @MixinClasses(RenderInformals.class)
    private Any container;

    public String getClientId() {
        return clientId;
    }

    public String getContainerClass() {
        return className != null ?
                CSSConstants.BUTTON_GROUP + " " + CSSConstants.DROPDOWN + " " + className :
                CSSConstants.BUTTON_GROUP + " " + CSSConstants.DROPDOWN;
    }

    public String getToggleClass() {
        return CSSConstants.BUTTON + " " + CSSConstants.DROPDOWN_TOGGLE;
    }

    public String getCaretClass() {
        return CSSConstants.CARET;
    }

    public String getMenuClass() {
        return CSSConstants.DROPDOWN_MENU;
    }
}
