package com.mutabra.web.components.game;

import com.mutabra.domain.common.Face;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceDisplay {

	@Property
	@Parameter(required = true)
	private Face face;

	@Inject
	private AssetSource assetSource;

	public Asset getFaceImage() {
		try {
			return assetSource.getContextAsset("img/faces/" + face.getCode().toLowerCase() + ".png", null);
		} catch (Exception e) {
			return null;
		}
	}
}
