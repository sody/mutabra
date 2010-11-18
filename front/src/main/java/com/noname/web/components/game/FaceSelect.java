package com.noname.web.components.game;

import com.noname.domain.common.Face;
import com.noname.services.common.FaceService;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.greatage.tapestry.internal.SelectModelBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceSelect {

	@Property
	@Parameter
	private Face face;

	@Inject
	private FaceService faceService;

	@Inject
	private SelectModelBuilder selectModelBuilder;

	private List<Face> faces;

	@Property
	private Object row;

	@Inject
	private AssetSource assetSource;

	public List<Face> getFaces() {
		if (faces == null) {
			faces = faceService.getEntities();
		}
		return faces;
	}

	public Asset getFaceImage() {
		final String code = ((Face) row).getCode();
		return assetSource.getContextAsset("img/faces/" + code.toLowerCase() + ".png", null);
	}
}
