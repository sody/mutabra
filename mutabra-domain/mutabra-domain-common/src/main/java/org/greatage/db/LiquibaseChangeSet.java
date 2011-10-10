package org.greatage.db;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.sql.DataSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LiquibaseChangeSet implements ChangeSet {
	private boolean skip;
	private boolean dropFirst;
	private String contexts;
	private String changeLog;

	private final DataSource dataSource;
	private final ResourceAccessor resourceAccessor;

	protected LiquibaseChangeSet(final DataSource dataSource) {
		this(dataSource, new ClassLoaderResourceAccessor());
	}

	protected LiquibaseChangeSet(final DataSource dataSource, final ResourceAccessor resourceAccessor) {
		this.dataSource = dataSource;
		this.resourceAccessor = resourceAccessor;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(final boolean skip) {
		this.skip = skip;
	}

	public boolean isDropFirst() {
		return dropFirst;
	}

	public void setDropFirst(final boolean dropFirst) {
		this.dropFirst = dropFirst;
	}

	public String getContexts() {
		return contexts;
	}

	public void setContexts(final String contexts) {
		this.contexts = contexts;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(final String changeLog) {
		this.changeLog = changeLog;
	}

	public void execute() {
		if (isSkip()) {
//			log.info("LiquiBase skipped due to skip flag configuration");
			return;
		}

		Liquibase liquibase = null;
		try {
			liquibase = createLiquibase();
			if (isDropFirst()) {
				liquibase.dropAll();
			}
			liquibase.update(getContexts());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (liquibase != null) {
				try {
					liquibase.forceReleaseLocks();
				} catch (LiquibaseException e) {
//					log.warning("Error releasing locks", e);
				}
				final Database database = liquibase.getDatabase();
				try {
					if (!database.isAutoCommit()) {
						database.rollback();
					}
					database.close();
				} catch (Exception e) {
//					log.warning("problem closing database", e);
				}
			}
		}
	}

	protected Liquibase createLiquibase() throws Exception {
		return new Liquibase(getChangeLog(), resourceAccessor, getDatabase());
	}

	protected Database getDatabase() throws Exception {
		return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(getDatabaseConnection());
	}

	protected DatabaseConnection getDatabaseConnection() throws Exception {
		return new JdbcConnection(dataSource.getConnection());
	}
}
