/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractClientElement;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroFaceSelect extends AbstractClientElement {

    @Parameter(required = true, allowNull = false)
    private GridDataSource source;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String target;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private HeroAppearancePart part;

    @Property
    private Object item;

    public String getItemValue() {
        return encode(source.getRowType(), item);
    }
}
