package com.mutabra.web.components.image;

import com.mutabra.domain.common.Face;
import com.mutabra.web.base.components.AbstractImage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceImage extends AbstractImage {

	@Property
	@Parameter(required = true, allowNull = false)
	private Face face;

	@Override
	protected String getTitle() {
		return face.getCode();
	}

	@Override
	protected String getAlt() {
		return face.getCode();
	}

	@Override
	protected String getPath() {
		return "img/faces/";
	}

	@Override
	protected String getName() {
		return face.getCode().toLowerCase();
	}
}
