/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages;

import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.NotFoundException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.Response;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Khalopik
 */
public class Error extends AbstractPage implements ExceptionReporter {

    private int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    @Inject
    private Block defaultBlock;

    @Inject
    private Response response;

    @Override
    public String getTitle() {
        return message("title." + status);
    }

    @Override
    public String getHeader() {
        return message("header." + status);
    }

    public Block getContent() {
        final Block block = getResources().getBlock("error" + status);
        return block != null ? block : defaultBlock;
    }

    @Override
    public void reportException(final Throwable exception) {
        final Throwable rootCause = getRootCause(exception);
        if (rootCause instanceof NotFoundException) {
            setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (rootCause instanceof AuthorizationException) {
            setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void setStatus(final int status) {
        response.setStatus(status);
        this.status = status;
    }

    private static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
        }
        return throwable;
    }
}
