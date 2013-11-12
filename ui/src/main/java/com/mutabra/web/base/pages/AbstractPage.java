/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.base.pages;

import com.mutabra.web.base.components.AbstractComponent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbstractPage extends AbstractComponent {

    public String getTitle() {
        return message("title");
    }

    public String getHeader() {
        return message("header");
    }

    public String getHeaderNote() {
        return getMessages().contains("header.note") ? message("header.note") : null;
    }
}