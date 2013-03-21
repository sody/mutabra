package com.mutabra.web.services;

import org.apache.tapestry5.internal.parser.ComponentTemplate;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ImageSource {

    ComponentTemplate getImage(String type, String image);

    ComponentTemplate getImage(String path);
}
