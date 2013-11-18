package com.mutabra.web.internal;

import com.mutabra.domain.Translatable;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.Messages;

/**
 * @author Ivan Khalopik
 */
public abstract class MutabraInternalUtils {


    public static String getLabel(final Messages messages,
                                  final Enum<?> value) {
        return TapestryInternalUtils.getLabelForEnum(messages, value);
    }

    public static String getTranslation(final Messages messages,
                                        final Translatable translatable) {
        return messages.get(translatable.getBasename() + '.' + translatable.getCode());
    }

    public static String getTranslation(final Messages messages,
                                        final Translatable translatable,
                                        final String variant) {
        return messages.get(translatable.getBasename() + '.' + translatable.getCode() + '.' + variant);
    }

}
