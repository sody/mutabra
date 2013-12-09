/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.hero;

import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.web.base.components.AbstractClientElement;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroFaceDisplay extends AbstractClientElement {

    @Parameter(required = true, allowNull = false)
    private HeroAppearance appearance;

    @Parameter
    private Iterable<HeroAppearancePart> parts;

    @Parameter(value = "100")
    private int width;

    @Parameter(value = "100")
    private int height;

    @Parameter(value = "0")
    private int x;

    @Parameter(value = "0")
    private int y;

    @Parameter
    private boolean inline;

    @Parameter(value = "#333", defaultPrefix = BindingConstants.LITERAL)
    private String stroke;

    private Iterator<HeroAppearancePart> iterator;
    private HeroAppearancePart part;

    Iterable<HeroAppearancePart> defaultParts() {
        return EnumSet.range(HeroAppearancePart.EARS, HeroAppearancePart.FACIAL_HAIR);
    }

    public HeroAppearance getAppearance() {
        return appearance;
    }

    public String getStrokeColor() {
        return stroke;
    }

    public HeroAppearancePart getPart() {
        return part;
    }

    @SetupRender
    void setup(final MarkupWriter writer) {
        // calculate attributes
        final double scaleX = (double) width / 100;
        final double scaleY = (double) height / 100;
        final StringBuilder transform = new StringBuilder();
        if (x != 0 || y != 0) {
            transform.append(String.format("translate(%d,%d) ", x, y));
        }
        transform.append(String.format(Locale.US, "scale(%.3f,%.3f)", scaleX, scaleY));

        // svg is not rendered in inline mode
        if (!inline) {
            writer.elementNS("http://www.w3.org/2000/svg", "svg").attributes(
                    "id", getClientId(),
                    "width", String.valueOf(width),
                    "height", String.valueOf(height),
                    "version", "1.1");
        }
        writer.elementNS("http://www.w3.org/2000/svg", "g").attributes(
                "width", String.valueOf(width),
                "height", String.valueOf(height),
                "transform", transform.toString(),
                "data-part", "all");

        // render name as title
        writer.element("title");
        writer.write(appearance.getName());
        writer.end();

        iterator = parts.iterator();
    }

    @BeginRender
    Object begin() {
        final HeroAppearancePart nextPart = iterator.next();
        final Block block = getResources().findBlock(nextPart.name().toLowerCase());
        return block != null ? block : Boolean.FALSE;
    }

    @AfterRender
    boolean end() {
        return !iterator.hasNext();
    }

    @CleanupRender
    void cleanup(final MarkupWriter writer) {
        writer.end(); // g
        // svg is not rendered in inline mode
        if (!inline) {
            writer.end(); // svg
        }
    }
}
