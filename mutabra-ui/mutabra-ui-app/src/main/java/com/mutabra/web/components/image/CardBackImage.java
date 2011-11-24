package com.mutabra.web.components.image;

import com.mutabra.web.base.components.AbstractImage;
import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardBackImage extends AbstractImage {

	@Inject
	private ImageSource imageSource;

	@Override
	protected String getTitle() {
		return getMessages().get("label.closed-card");
	}

	@Override
	protected String getAlt() {
		return "closed-card";
	}

	@Override
	protected Asset getAsset() {
		return imageSource.getCardBack();
	}
}
