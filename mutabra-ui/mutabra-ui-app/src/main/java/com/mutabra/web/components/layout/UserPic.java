package com.mutabra.web.components.layout;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class UserPic extends AbstractComponent {

	@Parameter(value = "64")
	private int size;

	@Parameter(value = "literal:anonymous")
	private String user;

	@Inject
	private AssetSource assetSource;

	void beginRender(final MarkupWriter writer) {
		final Element img = writer.element("img");
		getResources().renderInformalParameters(writer);

		final StringBuilder builder = new StringBuilder("img/faces/").append(user);
		builder.append('_').append(size).append('x').append(size).append(".png");
		final Asset asset = assetSource.getContextAsset(builder.toString(), getLocale());

		img.attribute("src", asset.toClientURL());
		writer.end();
	}
}
