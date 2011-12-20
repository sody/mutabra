package com.mutabra.web.internal;

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.meta.MetaDataExtractor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthorityAnnotationExtractor implements MetaDataExtractor<Authority> {

	public void extractMetaData(final MutableComponentModel model, final Authority annotation) {
		if (model.isPage()) {
			model.setMeta(Authorities.PAGE_AUTHORITY_META, Authorities.permission(annotation.value()));
		}
	}
}
