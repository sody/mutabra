package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ChangeLog {
	private static final String DEFAULT_AUTHOR = "<unknown>";

	private Database database;

	protected ChangeSetBuilder begin(final String title) {
		return begin(title, DEFAULT_AUTHOR);
	}

	protected ChangeSetBuilder begin(final String title, final String author) {
		assert database != null;
		assert title != null;
		assert author != null;

		return database.changeSet(title, author, getClass().getName());
	}

	protected void add(final ChangeLog changeLog) {
		assert database != null;
		assert changeLog != null;

		changeLog.execute(database);
	}

	protected abstract void init();

	public final void execute(final Database database) {
		this.database = database;
		init();
		this.database = null;
	}
}
