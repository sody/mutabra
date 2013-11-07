package com.mutabra.web.internal;

import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.internal.parser.ComponentTemplate;
import org.apache.tapestry5.internal.services.TemplateParser;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.URLChangeTracker;
import org.apache.tapestry5.ioc.services.ClasspathURLConverter;
import org.apache.tapestry5.services.UpdateListener;
import org.apache.tapestry5.services.UpdateListenerHub;

import java.util.Map;

/**
 * @author Ivan Khalopik
 */
public class ImageSourceImpl implements ImageSource, UpdateListener {
    private final Map<String, ComponentTemplate> templates = CollectionFactory.newConcurrentMap();

    private final TemplateParser parser;
    private final URLChangeTracker tracker;
    private final Resource unknown;

    public ImageSourceImpl(final TemplateParser parser,
                           final ClasspathURLConverter classpathURLConverter,
                           @Path("${mutabra.asset.root}/svg/unknown.svg")
                           final Resource unknown) {
        // validate
        if (!unknown.exists()) {
            throw new IllegalStateException("Unknown SVG placeholder should exist.");
        }

        this.parser = parser;
        this.tracker = new URLChangeTracker(classpathURLConverter);
        this.unknown = unknown;
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

        final Resource resource = unknown.forFile(path).withExtension("svg");
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
