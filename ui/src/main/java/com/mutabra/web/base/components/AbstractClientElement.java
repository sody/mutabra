/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.base.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractClientElement extends AbstractComponent implements ClientElement {

    @Parameter(name = "id", defaultPrefix = BindingConstants.LITERAL)
    private String idParameter;

    private String clientId;

    @Environmental
    private JavaScriptSupport jsSupport;

    public String getBoundId() {
        // just for caching
        return idParameter;
    }

    @Cached(watch = "boundId")
    public String getClientId() {
        // calculate client id
        return getResources().isBound("id") ? idParameter : jsSupport.allocateClientId(getResources());
    }

    public JavaScriptSupport getJavaScriptSupport() {
        return jsSupport;
    }
}