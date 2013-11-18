/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.auth;

import com.mutabra.web.base.pages.AbstractAuthPage;
import com.mutabra.web.internal.annotations.AuthMenu;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mutabra.web.internal.annotations.AuthMenuItem.SIGN_IN;

/**
 * @author Ivan Khalopik
 */
@AuthMenu(SIGN_IN)
public class SignInAuth extends AbstractAuthPage {

    @Property
    private String email;

    @Property
    private String password;

    @Property(write = false)
    private List<String> errors;

    @Inject
    private Request request;

    @SetupRender
    void setupRender() {
        errors = new ArrayList<String>();
        if (request.getParameter("error") != null) {
            // error request parameter means that authentication failed
            errors.add(message("error.sign-in"));
        } else if (request.getParameter("not_authenticated") != null) {
            // not_authenticated request parameter means that user is not authenticated
            errors.add(message("error.not-authenticated"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "signInForm")
    Object signIn() throws IOException {
        // try to sign in
        final String remoteHost = request.getRemoteHost();
        getSubject().login(new UsernamePasswordToken(email, password, remoteHost));

        return authenticated();
    }
}
