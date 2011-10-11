package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Database {

	void connect();

	void close();

	ChangeSetBuilder changeSet(String id, String author, String location);

}
