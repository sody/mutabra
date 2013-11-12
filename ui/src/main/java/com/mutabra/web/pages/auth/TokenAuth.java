/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.auth;

import com.mutabra.web.base.pages.AbstractAuthPage;
import com.mutabra.web.internal.security.ConfirmationRealm;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 */
public class TokenAuth extends AbstractAuthPage {

    @Inject
    private Request request;

    @OnEvent(value = EventConstants.ACTIVATE)
    Object authenticate(final ObjectId userId, final String token) throws IOException {
        final String remoteHost = request.getRemoteHost();
        getSubject().login(new ConfirmationRealm.Token(userId, token, remoteHost));

        return authenticated();
    }
}
