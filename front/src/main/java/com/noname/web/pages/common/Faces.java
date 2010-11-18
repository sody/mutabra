package com.noname.web.pages.common;

import com.noname.domain.common.Face;
import com.noname.services.common.FaceService;
import com.noname.web.base.pages.CodedEntityPage;
import com.noname.web.services.AuthorityConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.ROLE_ADMIN)
public class Faces extends CodedEntityPage<Face> {

	@Inject
	private FaceService faceService;

	@Override
	protected FaceService getEntityService() {
		return faceService;
	}
}