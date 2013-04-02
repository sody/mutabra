package com.mutabra.web.internal;

import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.internal.parser.ComponentTemplate;
import org.apache.tapestry5.internal.services.TemplateParser;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.URLChangeTracker;
import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.UpdateListener;
import org.apache.tapestry5.services.UpdateListenerHub;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ImageSourceImpl implements ImageSource, UpdateListener {
    private final Map<String, ComponentTemplate> templates = CollectionFactory.newConcurrentMap();

    private final AssetSource assetSource;
    private final TemplateParser parser;
    private final URLChangeTracker tracker;

    private final Resource unknown;

    public ImageSourceImpl(final AssetSource assetSource,
                           final TemplateParser parser,
                           final ClasspathURLConverter classpathURLConverter) {
        this.assetSource = assetSource;
        this.parser = parser;
        this.tracker = new URLChangeTracker(classpathURLConverter);

        //todo: inject default image using configured symbols
        unknown = assetSource.resourceForPath("unknown.svg");
    }

    @PostInjection
    public void registerAsUpdateListener(final UpdateListenerHub hub) {
        hub.addUpdateListener(this);
    }

    public void checkForUpdates() {
        if (tracker.containsChanges()) {
            tracker.clear();
            templates.clear();
        }
    }

    public ComponentTemplate getImage(final String type, final String image) {
        return getImage(type != null ? type + "/" + image : image);
    }

    public ComponentTemplate getImage(final String path) {
        final ComponentTemplate template = templates.get(path);
        if (template != null) {
            return template;
        }

        final Resource resource = assetSource.resourceForPath(path).withExtension("svg");
        final ComponentTemplate parsedTemplate = parseTemplate(resource);
        templates.put(path, parsedTemplate);
        return parsedTemplate;
    }

    private ComponentTemplate parseTemplate(final Resource resource) {
        if (resource.exists()) {
            tracker.add(resource.toURL());
            return parser.parseTemplate(resource);
        }
        return parseTemplate(unknown);
    }
}
