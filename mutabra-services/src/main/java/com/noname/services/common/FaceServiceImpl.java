package com.noname.services.common;

import com.noname.domain.common.Face;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceServiceImpl extends CodedEntityServiceImpl<Face, FaceQuery> implements FaceService {

	public FaceServiceImpl(final EntityRepository repository, final TranslationService translationService) {
		super(repository, translationService, Face.class, FaceQuery.class);
	}

}
