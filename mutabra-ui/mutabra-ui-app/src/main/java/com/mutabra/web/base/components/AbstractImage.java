package com.mutabra.web.base.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractImage extends AbstractComponent {

	@Parameter(value = "64")
	private int size;

	@Inject
	private AssetSource assetSource;

	@BeginRender
	protected void beginRender(final MarkupWriter writer) {
		final Element img = writer.element("img", "alt", getAlt(), "title", getTitle());
		getResources().renderInformalParameters(writer);

		final StringBuilder builder = new StringBuilder(getPath()).append(getName());
		builder.append('_').append(size).append('x').append(size).append(".png");
		final Asset asset = assetSource.getContextAsset(builder.toString(), getLocale());

		img.attribute("src", asset.toClientURL());
		writer.end();
	}

	protected String getAlt() {
		return "Image";
	}

	protected String getTitle() {
		return "Image";
	}

	protected String getPath() {
		return "";
	}

	protected abstract String getName();
}
