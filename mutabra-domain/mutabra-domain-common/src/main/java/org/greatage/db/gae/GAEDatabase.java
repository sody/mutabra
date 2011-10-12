package org.greatage.db.gae;

import com.google.appengine.api.datastore.*;
import org.greatage.db.*;

import java.util.Date;

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

	public synchronized void update(final ChangeLog changeLog) {
		lock();

		try {
			changeLog.execute(this);

			if (changeSet != null) {
				changeSet.end();
				changeSet = null;
			}
		} finally {
			unlock();
		}
	}

	public ChangeSetBuilder changeSet(final String title, final String author, final String location) {
		return beginChangeSet(new GAEChangeSet(this, title, author, location));
	}

	GAEChangeSet beginChangeSet(final GAEChangeSet changeSet) {
		if (this.changeSet != null) {
			this.changeSet.end();
		}
		this.changeSet = changeSet;
		return changeSet;
	}

	void endChangeSet(final GAEChangeSet changeSet) {
		final Query query = new Query(SystemTables.LOG.NAME)
				.addFilter(SystemTables.LOG.TITLE, Query.FilterOperator.EQUAL, changeSet.getTitle())
				.addFilter(SystemTables.LOG.AUTHOR, Query.FilterOperator.EQUAL, changeSet.getAuthor())
				.addFilter(SystemTables.LOG.LOCATION, Query.FilterOperator.EQUAL, changeSet.getLocation());
		final Entity logEntry = dataStore.prepare(query).asSingleEntity();
		if (logEntry == null) {
			changeSet.doInDataStore(dataStore);
			log(changeSet);
		} else {
			final String actual = changeSet.getCheckSum();
			final String expected = (String) logEntry.getProperty(SystemTables.LOG.CHECKSUM);
			if (!CheckSumUtils.isValid(expected)) {
				logEntry.setProperty(SystemTables.LOG.CHECKSUM, actual);
				dataStore.put(logEntry);
			} else if (!expected.equals(actual)) {
				throw new DatabaseException(String.format("CheckSum check failed for change set '%s : %s : %s'. Should be '%s' but was '%s'",
						changeSet.getTitle(), changeSet.getAuthor(), changeSet.getLocation(), expected, actual));
			}
		}
		this.changeSet = null;
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
