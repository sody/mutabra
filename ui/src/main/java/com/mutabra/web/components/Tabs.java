/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
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
public class Tabs extends AbstractComponent implements ClientElement {

    @Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String[] tabs;

    @Property
    @Parameter
    private String tab;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String active;

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
                CSSConstants.TABBABLE + " " + className :
                CSSConstants.TABBABLE;
    }

    public String getMenuClass() {
        return CSSConstants.NAV + " " + CSSConstants.NAV_TABS;
    }

    public String getMenuItemClass() {
        return tab.equals(active) ? CSSConstants.ACTIVE : null;
    }

    public String getTabContentClass() {
        return CSSConstants.TAB_CONTENT;
    }

    public String getTabId() {
        return clientId + "_" + tab;
    }

    public String getTabClass() {
        return tab.equals(active) ?
                CSSConstants.TAB_PANE + " " + CSSConstants.ACTIVE :
                CSSConstants.TAB_PANE;
    }

    public String getTabTitle() {
        return label(tab);
    }

    public Block getTabBody() {
        final Block block = overrides.getOverrideBlock(tab + "Tab");
        if (block != null) {
            return block;
        }
        if (getResources().hasBody()) {
            return getResources().getBody();
        }
        return null;
    }

    public boolean isTabsBelow() {
        return className != null && className.contains(CSSConstants.TABS_BELOW);
    }
}
