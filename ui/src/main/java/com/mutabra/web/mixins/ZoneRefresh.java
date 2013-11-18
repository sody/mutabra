/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.mixins;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * <p>
 * This mixin periodically refreshs a @{link org.apache.tapestry5.corelib.components.Zone zone}
 * by triggering an event on the server using ajax requests.
 * </p>
 * <p/>
 * <b>Note: </b> This mixin is only meant for a @{link org.apache.tapestry5.corelib.components.Zone zone}
 *
 * @tapestrydoc
 */
@Import(library = "zone-refresh.js")
public class ZoneRefresh {
    /**
     * Period between two consecutive refreshes (in seconds)
     */
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private int period;

    /**
     * Context passed to the event
     */
    @Parameter
    private Object[] context;

    @InjectContainer
    private Zone zone;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    public ZoneRefresh() {
    }

    //For testing purpose
    ZoneRefresh(Object[] context, ComponentResources resources, JavaScriptSupport javaScriptSupport, Zone zone) {
        this.context = context;
        this.resources = resources;
        this.javaScriptSupport = javaScriptSupport;
        this.zone = zone;
    }

    @AfterRender
    void addJavaScript() {
        JSONObject params = new JSONObject();

        params.put("period", period);
        params.put("id", zone.getClientId());
        params.put("URL", createEventLink());

        javaScriptSupport.addInitializerCall(InitializationPriority.LATE, "zoneRefresh", params);
    }

    private Object createEventLink() {
        Link link = resources.createEventLink("zoneRefresh", context);
        return link.toAbsoluteURI();
    }

    Object onZoneRefresh(EventContext eventContext) {
        CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
        resources.triggerContextEvent(EventConstants.REFRESH, eventContext, callback);

        if (callback.getResult() != null) {
            return callback.getResult();
        }

        return zone.getBody();
    }
}