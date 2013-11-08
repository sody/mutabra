/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal;

import com.mutabra.web.internal.annotations.Naming;
import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ObjectProvider;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class NamingObjectProvider implements ObjectProvider {
    public <T> T provide(final Class<T> objectType,
                         final AnnotationProvider annotationProvider,
                         final ObjectLocator locator) {
        final Naming annotation = annotationProvider.getAnnotation(Naming.class);
        if (annotation != null) {
            final String name = annotation.value();

            try {
                final Context ctx = new InitialContext();
                final Object manager = ctx.lookup(name);
                return objectType.cast(manager);
            } catch (NamingException e) {
                throw new RuntimeException("Unable to lookup resource.", e);
            }
        }
        return null;
    }
}
