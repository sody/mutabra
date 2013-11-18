/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.base.components;

import com.mutabra.domain.Translatable;
import com.mutabra.web.internal.MutabraInternalUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderSource;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbstractComponent {

    @Inject
    private ComponentResources resources;

    @Inject
    private Messages messages;

    @Inject
    private ValueEncoderSource encoderSource;

    @Inject
    private Locale locale;

    public ComponentResources getResources() {
        return resources;
    }

    public Messages getMessages() {
        return messages;
    }

    public Locale getLocale() {
        return locale;
    }

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected <T> String encode(final Class<T> type, final T value) {
        return encoderSource.getValueEncoder(type).toClient(value);
    }

    protected <T> T decode(final Class<T> type, final String value) {
        return encoderSource.getValueEncoder(type).toValue(value);
    }

    protected String format(final String key, final Object... parameters) {
        return messages.format(key, parameters);
    }

    protected String message(final String key) {
        return messages.get(key);
    }

    protected String message(final Enum<?> value) {
        return MutabraInternalUtils.getLabel(messages, value);
    }

    protected String translate(final Translatable translatable) {
        return MutabraInternalUtils.getTranslation(messages, translatable);
    }

    protected String translate(final Translatable translatable, final String variant) {
        return MutabraInternalUtils.getTranslation(messages, translatable, variant);
    }
}
