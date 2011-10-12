package org.greatage.db.gae;

import com.google.appengine.api.datastore.*;
import org.greatage.db.ChangeLog;
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
	private final DatastoreService dataStore;

	private Map<CompositeKey, Entity> logs = new HashMap<CompositeKey, Entity>();
	private ChangeSetBuilder changeSet;

	public GAEDatabase() {
		this(DatastoreServiceFactory.getDatastoreService());
	}

	public GAEDatabase(final DatastoreService dataStore) {
		this.dataStore = dataStore;
	}

	public synchronized void update(final ChangeLog changeLog) {
		lock();
		logs.clear();
		for (Entity entity : dataStore.prepare(new Query(SystemTables.LOG.NAME)).asIterable()) {
			final String id = (String) entity.getProperty(SystemTables.LOG.TITLE);
			final String author = (String) entity.getProperty(SystemTables.LOG.AUTHOR);
			final String location = (String) entity.getProperty(SystemTables.LOG.LOCATION);
			final CompositeKey key = new CompositeKey(id, author, location);
			logs.put(key, entity);
		}

		changeLog.execute(this);

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
			lock = dataStore.get(KeyFactory.createKey(SystemTables.LOCK.NAME, SystemTables.LOCK.ID));
		} catch (EntityNotFoundException e) {
			// it doesn't exist, so we need to create one
		}
		if (lock == null) {
			lock = new Entity(SystemTables.LOCK.NAME, SystemTables.LOCK.ID);
		}
		if (lock.hasProperty(SystemTables.LOCK.LOCKED_AT)) {
			//noinspection MalformedFormatString
			throw new DatabaseException(String.format("Already locked at '%1$tF %1$tT'. Skipping update.",
					lock.getProperty(SystemTables.LOCK.LOCKED_AT)));
		}
		lock.setProperty(SystemTables.LOCK.LOCKED_AT, new Date());
		dataStore.put(lock);
	}

	private void unlock() {
		Entity lock = null;
		try {
			lock = dataStore.get(KeyFactory.createKey(SystemTables.LOCK.NAME, SystemTables.LOCK.ID));
		} catch (EntityNotFoundException e) {
			// it doesn't exist, so we need to create one
		}
		if (lock != null && lock.hasProperty(SystemTables.LOCK.LOCKED_AT)) {
			lock.removeProperty(SystemTables.LOCK.LOCKED_AT);
			dataStore.put(lock);
		}
	}

	private void log(final GAEChangeSet changeSet) {
		final Entity logEntry = new Entity(SystemTables.LOG.NAME);
		logEntry.setProperty(SystemTables.LOG.TITLE, changeSet.getTitle());
		logEntry.setProperty(SystemTables.LOG.AUTHOR, changeSet.getAuthor());
		logEntry.setProperty(SystemTables.LOG.LOCATION, changeSet.getLocation());
		logEntry.setProperty(SystemTables.LOG.COMMENT, changeSet.getComment());
		logEntry.setProperty(SystemTables.LOG.CHECKSUM, changeSet.getCheckSum());
		logEntry.setProperty(SystemTables.LOG.EXECUTED_AT, new Date());
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

	interface SystemTables {
		interface LOCK {
			long ID = 1l;

			String NAME = "DATABASE_CHANGE_LOG_LOCK";
			String LOCKED_AT = "lockedAt";
		}

		interface LOG {
			String NAME = "DATABASE_CHANGE_LOG";
			String TITLE = "title";
			String AUTHOR = "author";
			String LOCATION = "location";
			String COMMENT = "comment";
			String CHECKSUM = "checkSum";
			String EXECUTED_AT = "executedAt";
		}
	}
}
