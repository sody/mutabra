package com.mutabra.domain;

import com.google.appengine.api.datastore.Key;
import org.greatage.util.CollectionUtils;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Keys {
	private static volatile PersistenceManagerFactory instance;

	public static void init(final PersistenceManagerFactory instance) {
		Keys.instance = instance;
	}

	public static <T> T getInstance(final Key key, final Class<T> entityClass) {
		final PersistenceManager manager = instance.getPersistenceManager();
		return manager.getObjectById(entityClass, key);
	}

	public static <T, V extends T> Set<T> getInstances(final Set<Key> keys, final Class<T> entityClass, final Class<V> valueClass) {
		final PersistenceManager manager = instance.getPersistenceManager();
		final Set<T> result = CollectionUtils.newSet();
		for (Key key : keys) {
			result.add(manager.getObjectById(valueClass, key));
		}
		return result;
	}

	public static <T extends BaseEntity> Key getKey(final T entity) {
		return ((BaseEntityImpl) entity).getKey();
	}

	public static <T extends BaseEntity> Set<Key> getKeys(final Set<T> entities) {
		final Set<Key> result = CollectionUtils.newSet();
		for (T entity : entities) {
			result.add(((BaseEntityImpl) entity).getKey());
		}
		return result;
	}
}
