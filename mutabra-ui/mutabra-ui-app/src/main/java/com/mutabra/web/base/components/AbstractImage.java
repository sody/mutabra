package com.mutabra.web.base.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public abstract class AbstractImage extends AbstractComponent {

    @Parameter(value = "64")
    private int width;

    @Parameter(value = "64")
    private int height;

    @BeginRender
    protected void beginRender(final MarkupWriter writer) {
        final Element img = writer.element("img",
                "alt", getAlt(),
                "title", getTitle(),
                "width", getWidth(),
                "height", getHeight());
        getResources().renderInformalParameters(writer);

        img.attribute("src", getAsset().toClientURL());
        writer.end();
    }

    protected String getAlt() {
        return "Image";
    }

    protected String getTitle() {
        return "Image";
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected abstract Asset getAsset();
}
