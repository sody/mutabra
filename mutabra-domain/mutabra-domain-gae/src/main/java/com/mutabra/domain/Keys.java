package com.mutabra.domain;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import org.greatage.domain.SessionCallback;
import org.greatage.domain.TransactionExecutor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Keys {
	private static volatile TransactionExecutor<Transaction, Objectify> executor;

	public static void init(final TransactionExecutor<Transaction, Objectify> executor) {
		Keys.executor = executor;
	}

	public static <T> T getInstance(final Key<T> key) {
		return key == null ? null : executor.execute(new SessionCallback<T, Objectify>() {
			public T doInSession(final Objectify session) throws Exception {
				return session.get(key);
			}
		});
	}

	public static <T, V extends T> Set<T> getInstances(final Class<T> entityClass, final Set<Key<V>> keys) {
		return keys == null ? null : executor.execute(new SessionCallback<Set<T>, Objectify>() {
			public Set<T> doInSession(final Objectify session) throws Exception {
				return new HashSet<T>(session.get(keys).values());
			}
		});
	}

	public static <T, V extends T, P> Set<T> getChildren(final Class<T> entityClass, final Class<V> realClass, final P parent) {
		return executor.execute(new SessionCallback<Set<T>, Objectify>() {
			public Set<T> doInSession(final Objectify session) throws Exception {
				return new HashSet<T>(session.query(realClass).ancestor(parent).list());
			}
		});
	}
}
