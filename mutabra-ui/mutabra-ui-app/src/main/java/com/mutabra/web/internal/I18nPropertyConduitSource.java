package com.mutabra.web.internal;

import com.mutabra.web.services.Translator;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.services.PropertyConduitSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class I18nPropertyConduitSource implements PropertyConduitSource {
    private static final Pattern I18N_PATTERN = Pattern.compile("^(.+):(.+)$");

    private final PropertyConduitSource conduitSource;
    private final Translator translator;
    private final StringInterner interner;

    public I18nPropertyConduitSource(final PropertyConduitSource conduitSource, final Translator translator, final StringInterner interner) {
        assert conduitSource != null;
        assert translator != null;
        assert interner != null;

        this.translator = translator;
        this.conduitSource = conduitSource;
        this.interner = interner;
    }

    public PropertyConduit create(final Class rootType, final String expression) {
        final Matcher matcher = I18N_PATTERN.matcher(expression);
        if (matcher.matches()) {
            final String propertyExpression = matcher.group(1);
            final String variant = matcher.group(2);
            final PropertyConduit conduit = conduitSource.create(rootType, propertyExpression);
            final String description = interner.format("I18nConduit[%s %s %s]", rootType.getName(), propertyExpression, variant);
            return new I18nConduit(conduit, translator, variant, description);
        }
        return conduitSource.create(rootType, expression);
    }

}