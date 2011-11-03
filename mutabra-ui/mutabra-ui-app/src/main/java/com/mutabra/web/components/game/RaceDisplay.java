package com.mutabra.web.components.game;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceDisplay extends AbstractComponent {

	@Property
	@Parameter(required = true, allowNull = false)
	private Race value;

	@Parameter(value = "64")
	private int size;

	@Inject
	private AssetSource assetSource;

	@BeginRender
	void beginRender(final MarkupWriter writer) {
		final Element img = writer.element("img", "alt", value.getCode(), "title", value.getCode());
		getResources().renderInformalParameters(writer);

		final StringBuilder builder = new StringBuilder("img/races/").append(value.getCode().toLowerCase());
		builder.append('_').append(size).append('x').append(size).append(".png");
		final Asset asset = assetSource.getContextAsset(builder.toString(), getLocale());

		img.attribute("src", asset.toClientURL());
		writer.end();
	}
}
