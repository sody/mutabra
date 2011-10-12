package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ConditionBuilder<T extends ChildBuilder<ChangeSetBuilder>> extends ChildBuilder<T> {

	ConditionEntryBuilder<T> and(String propertyName);

}
