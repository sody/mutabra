package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Face;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.common.FaceQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.FaceDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Faces extends AbstractPage {

	@InjectService("faceService")
	private BaseEntityService<Face, FaceQuery> faceService;

	@InjectComponent
	private FaceDialog entityDialog;

	@Property
	private Face row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Face>(faceService.query(), Face.class);
	}

	Object onEdit(final Face face) {
		return entityDialog.show(face);
	}

	Object onSuccess() {
		faceService.saveOrUpdate(entityDialog.getValue());
		return this;
	}
}
