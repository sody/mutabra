package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import org.greatage.db.ChangeSetBuilder;
import org.greatage.db.Database;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDatabase implements Database {
	private final DatastoreService dataStore;

	private ChangeSetBuilder changeSet;

	public GAEDatabase() {
		this(DatastoreServiceFactory.getDatastoreService());
	}

	public GAEDatabase(final DatastoreService dataStore) {
		this.dataStore = dataStore;
	}

	public void connect() {
		// do nothing
	}

	public void close() {
		// do nothing
	}

	public ChangeSetBuilder changeSet(final String id, final String author) {
		return beginChangeSet(new GAEChangeSet(this, id, author));
	}

	GAEChangeSet beginChangeSet(final GAEChangeSet changeSet) {
		if (this.changeSet != null) {
			this.changeSet.end();
		}
		this.changeSet = changeSet;
		return changeSet;
	}

	void endChangeSet(final GAEChangeSet changeSet) {
		changeSet.doInDataStore(dataStore);
		this.changeSet = null;
	}
}
