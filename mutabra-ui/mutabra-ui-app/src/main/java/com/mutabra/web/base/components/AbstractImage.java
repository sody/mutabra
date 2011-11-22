package com.mutabra.web.base.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractImage extends AbstractComponent {

	@Parameter(value = "64")
	private int size;

	@BeginRender
	protected void beginRender(final MarkupWriter writer) {
		final Element img = writer.element("img",
				"alt", getAlt(),
				"title", getTitle(),
				"width", getSize(),
				"height", getSize());
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

	protected int getSize() {
		return size;
	}

	protected abstract Asset getAsset();
}
