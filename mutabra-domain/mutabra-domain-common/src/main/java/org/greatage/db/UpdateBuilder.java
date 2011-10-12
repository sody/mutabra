package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface UpdateBuilder extends ChildBuilder<ChangeSetBuilder> {

	UpdateBuilder set(String propertyName, Object value);

	ConditionEntryBuilder<UpdateBuilder> where(String property);

}
