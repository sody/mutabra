package com.mutabra.services;

import com.mutabra.domain.CodedEntity;
import org.greatage.domain.EntityCriteria;
import org.greatage.util.StringUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityFilterProcessor extends BaseEntityFilterProcessor<CodedEntity, CodedEntityFilter<CodedEntity>> {

	public CodedEntityFilterProcessor() {
		super(CodedEntity.class);
	}

	@Override
	protected void processFilter(final EntityCriteria criteria, final CodedEntityFilter<CodedEntity> filter) {
		if (!StringUtils.isEmpty(filter.getCode())) {
			criteria.add(criteria.getProperty(CodedEntity.CODE_PROPERTY).eq(filter.getCode()));
		}
	}
}
