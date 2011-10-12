package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface DeleteBuilder extends ChildBuilder<ChangeSetBuilder> {

	ConditionEntryBuilder<DeleteBuilder> where(String propertyName);

}
