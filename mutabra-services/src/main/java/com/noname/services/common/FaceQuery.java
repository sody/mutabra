package com.noname.services.common;

import com.mutabra.domain.common.Face;
import com.noname.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceQuery extends CodedEntityQuery<Face, FaceQuery> implements FaceFilter {

	public FaceQuery() {
		super(Face.class);
	}

}
