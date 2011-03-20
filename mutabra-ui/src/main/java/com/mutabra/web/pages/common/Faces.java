package com.mutabra.web.pages.common;

import com.mutabra.domain.common.Face;
import com.mutabra.services.common.FaceService;
import com.mutabra.web.base.pages.CodedEntityPage;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.ROLE_ADMIN)
public class Faces extends CodedEntityPage<Face> {

	@Inject
	private FaceService faceService;

	@Override
	protected FaceService getEntityService() {
		return faceService;
	}
}