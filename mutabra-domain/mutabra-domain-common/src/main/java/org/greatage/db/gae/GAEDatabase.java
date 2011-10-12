package org.greatage.db.gae;

import com.google.appengine.api.datastore.*;
import org.greatage.db.ChangeSetBuilder;
import org.greatage.db.Database;
import org.greatage.db.DatabaseException;
import org.greatage.util.CompositeKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEDatabase implements Database {
	private static final String LOG_TABLE = "DATABASE_CHANGE_LOG";
	private static final String LOCK_TABLE = "DATABASE_CHANGE_LOG_LOCK";

	private final DatastoreService dataStore;

	private Map<CompositeKey, Entity> logs = new HashMap<CompositeKey, Entity>();
	private ChangeSetBuilder changeSet;

	public GAEDatabase() {
		this(DatastoreServiceFactory.getDatastoreService());
	}

	public GAEDatabase(final DatastoreService dataStore) {
		this.dataStore = dataStore;
	}

	public void connect() {
		lock();

		logs.clear();
		for (Entity entity : dataStore.prepare(new Query(LOG_TABLE)).asIterable()) {
			final String id = (String) entity.getProperty("title");
			final String author = (String) entity.getProperty("author");
			final String location = (String) entity.getProperty("location");
			final CompositeKey key = new CompositeKey(id, author, location);

			logs.put(key, entity);
		}
	}

	public void close() {
		if (changeSet != null) {
			changeSet.end();
			changeSet = null;
		}
		logs.clear();
		unlock();
	}

	public ChangeSetBuilder changeSet(final String title, final String author, final String location) {
		return beginChangeSet(new GAEChangeSet(this, title, author, location));
	}

	private void lock() {
		Entity lock = null;
		try {
			lock = dataStore.get(KeyFactory.createKey(LOCK_TABLE, 1l));
		} catch (EntityNotFoundException e) {
			// it doesn't exist, so we need to create one
		}
		if (lock == null) {
			lock = new Entity(LOCK_TABLE, 1l);
		}
		if (lock.hasProperty("lockedBy")) {
			throw new DatabaseException(String.format("Already locked by '%s'. Skipping update.", lock.getProperty("lockedBy")));
		}
		lock.setProperty("lockedBy", "me");
		dataStore.put(lock);
	}

	private void unlock() {
		Entity lock = null;
		try {
			lock = dataStore.get(KeyFactory.createKey(LOCK_TABLE, 1l));
		} catch (EntityNotFoundException e) {
			// it doesn't exist, so we need to create one
		}
		if (lock == null || !lock.hasProperty("lockedBy")) {
			throw new DatabaseException(String.format("Not locked. Skipping."));
		}
		lock.removeProperty("lockedBy");
		dataStore.put(lock);
	}

	private void log(final GAEChangeSet changeSet) {
		final Entity logEntry = new Entity(LOG_TABLE);
		logEntry.setProperty("title", changeSet.getTitle());
		logEntry.setProperty("author", changeSet.getAuthor());
		logEntry.setProperty("location", changeSet.getLocation());
		logEntry.setProperty("comment", changeSet.getComment());
		logEntry.setProperty("checkSum", changeSet.getCheckSum());
		logEntry.setProperty("executed", new Date());
		dataStore.put(logEntry);
	}

	GAEChangeSet beginChangeSet(final GAEChangeSet changeSet) {
		if (this.changeSet != null) {
			this.changeSet.end();
		}
		this.changeSet = changeSet;
		return changeSet;
	}

	void endChangeSet(final GAEChangeSet changeSet) {
		final CompositeKey key = new CompositeKey(changeSet.getTitle(), changeSet.getAuthor(), changeSet.getLocation());
		if (!logs.containsKey(key)) {
			changeSet.doInDataStore(dataStore);
			log(changeSet);
		}
		this.changeSet = null;
	}
}
