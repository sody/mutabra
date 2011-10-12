package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ConditionEntryBuilder;
import org.greatage.db.UpdateBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAEUpdate extends GAEConditionalStatement implements UpdateBuilder {
	private final Entity entity;

	GAEUpdate(final GAEChangeSet changeSet, final String entityName) {
		super(changeSet);
		this.entity = new Entity(entityName);
	}

	public UpdateBuilder set(final String propertyName, final Object value) {
		entity.setProperty(propertyName, value);
		return this;
	}

	public ConditionEntryBuilder<UpdateBuilder> where(final String property) {
		return new GAECondition<UpdateBuilder>(this).and(property);
	}

	public Object doInDataStore(final DatastoreService dataStore) {
		final Query query = new Query(entity.getKind());
		for (Query.FilterPredicate filter : getConditions()) {
			query.addFilter(filter.getPropertyName(), filter.getOperator(), filter.getValue());
		}

		for (Entity realEntity : dataStore.prepare(query).asIterable()) {
			realEntity.setPropertiesFrom(entity);
			dataStore.put(realEntity);
		}

		return null;
	}
}
