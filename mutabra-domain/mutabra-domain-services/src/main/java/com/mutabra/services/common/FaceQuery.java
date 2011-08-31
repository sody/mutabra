package com.mutabra.services.common;

import com.mutabra.domain.common.Face;
import com.mutabra.services.CodedEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceQuery extends CodedEntityQuery<Face, FaceQuery> {

	public FaceQuery(final EntityRepository repository) {
		super(repository, Face.class);
	}
}
