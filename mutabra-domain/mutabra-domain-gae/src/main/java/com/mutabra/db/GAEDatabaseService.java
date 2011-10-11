package com.mutabra.db;

import com.google.appengine.api.datastore.DatastoreService;
import org.greatage.db.Database;
import org.greatage.db.DatabaseService;
import org.greatage.db.gae.GAEDatabase;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDatabaseService implements DatabaseService {

	private final Database database;

	public GAEDatabaseService(final DatastoreService dataStore) {
		this.database = new GAEDatabase(dataStore);
	}

	public void update() {
		final MutabraChangeLog changeLog = new MutabraChangeLog();
		changeLog.execute(database);
	}
}
