package org.greatage.db.gae;

import org.greatage.db.ChangeSetBuilder;
import org.greatage.db.ChildBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class GAEStatement implements ChildBuilder<ChangeSetBuilder>, DataStoreCallback<Object> {
	private final GAEChangeSet changeSet;

	GAEStatement(final GAEChangeSet changeSet) {
		this.changeSet = changeSet;
	}

	public ChangeSetBuilder end() {
		return changeSet.endStatement(this);
	}
}
