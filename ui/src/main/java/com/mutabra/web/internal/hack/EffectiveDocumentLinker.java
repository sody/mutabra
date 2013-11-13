/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.internal.hack;

import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.internal.services.DocumentLinkerImpl;

import java.util.List;

/**
 * This class represents tapestry {@link org.apache.tapestry5.internal.services.DocumentLinker} hack
 * that adds script link for each included script to the bottom of the the {@code <body>} element.
 *
 * @author Ivan Khalopik
 */
public class EffectiveDocumentLinker extends DocumentLinkerImpl {

    public EffectiveDocumentLinker(final boolean omitGeneratorMetaTag, final String tapestryVersion, final boolean compactJSON) {
        super(omitGeneratorMetaTag, tapestryVersion, compactJSON);
    }

    @Override
    protected void addScriptLinksForIncludedScripts(final Element headElement, final List<String> scripts) {
        final Element root = headElement.getDocument().getRootElement();
        super.addScriptLinksForIncludedScripts(findOrCreateElement(root, "body", false), scripts);
    }

    private Element findOrCreateElement(final Element root, final String childElement, final boolean atTop) {
        final Element container = root.find(childElement);
        return container != null ? container :
                atTop ? root.elementAt(0, childElement) : root.element(childElement);
    }
}
