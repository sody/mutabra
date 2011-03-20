package com.mutabra.web.components.game;

import com.mutabra.domain.common.Face;
import com.mutabra.services.common.FaceService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

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

	private List<Face> faces;

	@Property
	private Object row;

	public List<Face> getFaces() {
		if (faces == null) {
			faces = faceService.getEntities();
		}
		return faces;
	}
}
