package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import org.greatage.db.ChangeSetBuilder;
import org.greatage.db.InsertBuilder;

import java.util.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEChangeSet implements ChangeSetBuilder, DataStoreCallback<Object> {
	private final GAEDatabase database;
	private final String title;
	private final String author;
	private final String location;

	private String comment;
	private Set<String> context = new HashSet<String>();

	private final List<GAEStatement> statements = new ArrayList<GAEStatement>();
	private GAEStatement statement;

	GAEChangeSet(final GAEDatabase database, final String title, final String author, final String location) {
		this.database = database;
		this.title = title;
		this.author = author;
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getLocation() {
		return location;
	}

	public String getComment() {
		return comment;
	}

	public String getCheckSum() {
		return "_FAKE_CHECK_SUM_";
	}

	public ChangeSetBuilder comment(final String comment) {
		this.comment = comment;
		return this;
	}

	public ChangeSetBuilder context(final String... context) {
		Collections.addAll(this.context, context);
		return this;
	}

	public InsertBuilder insert(final String entityName) {
		return beginStatement(new GAEInsert(this, entityName));
	}

	public void end() {
		database.endChangeSet(this);
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		//todo: do all work here
		for (GAEStatement gaeStatement : statements) {
			gaeStatement.doInDataStore(dataStore);
		}
		return null;
	}

	<T extends GAEStatement> T beginStatement(final T statement) {
		if (this.statement != null) {
			this.statement.end();
		}
		this.statement = statement;
		return statement;
	}

	<T extends GAEStatement> ChangeSetBuilder endStatement(final T statement) {
		statements.add(statement);
		this.statement = null;
		return this;
	}
}
