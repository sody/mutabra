package com.mutabra.domain;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import org.greatage.domain.SessionCallback;
import org.greatage.domain.TransactionExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Keys {
	private static volatile TransactionExecutor<Transaction, Objectify> executor;

	public static void init(final TransactionExecutor<Transaction, Objectify> executor) {
		Keys.executor = executor;
	}

	public static <T, V extends T> Key<V> getKey(final T entity) {
		if (entity != null) {
			return ((BaseEntityImpl) entity).getKey();
		}
		return null;
	}

	public static <T> T getInstance(final Key<T> key) {
		return key == null ? null : executor.execute(new SessionCallback<T, Objectify>() {
			public T doInSession(final Objectify session) throws Exception {
				return session.get(key);
			}
		});
	}

	public static <T, V extends T> List<T> getInstances(final Class<T> entityClass, final List<Key<V>> keys) {
		return keys == null ? null : executor.execute(new SessionCallback<List<T>, Objectify>() {
			public List<T> doInSession(final Objectify session) throws Exception {
				return new ArrayList<T>(session.get(keys).values());
			}
		});
	}

	public static <T, V extends T, P> List<T> getChildren(final Class<T> entityClass, final Class<V> realClass, final P parent) {
		return executor.execute(new SessionCallback<List<T>, Objectify>() {
			public List<T> doInSession(final Objectify session) throws Exception {
				return new ArrayList<T>(session.query(realClass).ancestor(parent).list());
			}
		});
	}
}
