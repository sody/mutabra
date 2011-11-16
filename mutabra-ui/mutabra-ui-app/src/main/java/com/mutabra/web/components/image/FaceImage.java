package com.mutabra.web.components.image;

import com.mutabra.domain.common.Face;
import com.mutabra.web.base.components.AbstractImage;
import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceImage extends AbstractImage {

	@Property
	@Parameter(required = true, allowNull = false)
	private Face face;

	@Parameter(value = "prop:face:description")
	private String title;

	@Inject
	private ImageSource imageSource;

	@Override
	protected String getTitle() {
		return title;
	}

	@Override
	protected Asset getAsset() {
		return imageSource.getFaceImage(face, getSize());
	}

	@Override
	protected String getAlt() {
		return face.getCode();
	}
}
