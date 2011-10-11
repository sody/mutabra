package com.mutabra.services.security;

import com.mutabra.domain.security.ChangeSet;
import com.mutabra.services.BaseEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ChangeSetQuery extends BaseEntityQuery<ChangeSet, ChangeSetQuery> {

	public ChangeSetQuery(final EntityRepository repository) {
		super(repository, ChangeSet.class);
	}
}
