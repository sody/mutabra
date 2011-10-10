package org.greatage.db;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultDatabaseService implements DatabaseService {

	private final List<ChangeSet> changeSets;

	public DefaultDatabaseService(final List<ChangeSet> changeSets) {
		this.changeSets = changeSets;
	}

	public void update() {
		for (ChangeSet changeSet : changeSets) {
			changeSet.execute();
		}
	}
}
