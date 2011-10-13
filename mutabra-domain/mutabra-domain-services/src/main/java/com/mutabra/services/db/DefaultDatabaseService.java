package com.mutabra.services.db;

import org.greatage.db.ChangeLog;
import org.greatage.db.Database;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultDatabaseService implements DatabaseService {
	private final Database database;
	private final ChangeLog changeLog;

	public DefaultDatabaseService(final Database database, final ChangeLog changeLog) {
		this.database = database;
		this.changeLog = changeLog;
	}

	public void update(final boolean dropFirst, final boolean clearCheckSums) {
		final Database.UpdateOptions updateOptions = database.options();
		if (dropFirst) {
			updateOptions.dropFirst();
		} else if (clearCheckSums) {
			updateOptions.clearCheckSums();
		}
		updateOptions.update(changeLog);
	}
}
