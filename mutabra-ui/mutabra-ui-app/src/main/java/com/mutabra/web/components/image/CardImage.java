package com.mutabra.web.components.image;

import com.mutabra.domain.common.Card;
import com.mutabra.web.base.components.AbstractImage;
import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Value;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardImage extends AbstractImage {

	@Property
	@Parameter(required = true, allowNull = false)
	private Card card;

	@Parameter(value = "prop:card:name")
	private String title;

	@Inject
	private ImageSource imageSource;

	@Override
	protected String getTitle() {
		return title;
	}

	@Override
	protected String getAlt() {
		return card.getCode();
	}

	@Override
	protected Asset getAsset() {
		return imageSource.getCardImage(card, getSize());
	}
}
