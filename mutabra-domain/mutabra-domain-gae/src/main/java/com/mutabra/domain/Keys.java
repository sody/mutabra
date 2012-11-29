package com.mutabra.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import org.greatage.domain.internal.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Keys {
    private static volatile SessionManager<Objectify> sessionManager;

    public static void init(final SessionManager<Objectify> manager) {
        Keys.sessionManager = manager;
    }

    public static <T, V extends T> Key<V> getKey(final T entity) {
        if (entity != null) {
            return ((BaseEntityImpl) entity).getKey();
        }
        return null;
    }

    public static <T> T getInstance(final Key<T> key) {
        return key == null ? null : sessionManager.execute(new SessionManager.Callback<T, Objectify>() {
            public T doInSession(final Objectify session) throws Exception {
                return session.get(key);
            }
        });
    }

    public static <T, V extends T> List<Key<V>> getKeys(final Class<V> entityClass, final List<T> entities) {
        final List keys = new ArrayList();
        for (T entity : entities) {
            if (entity != null) {
                keys.add(((BaseEntityImpl) entity).getKey());
            }
        }
        return keys;
    }

    public static <T, V extends T> List<T> getInstances(final Class<T> entityClass, final List<Key<V>> keys) {
        return keys == null ? null : sessionManager.execute(new SessionManager.Callback<List<T>, Objectify>() {
            public List<T> doInSession(final Objectify session) throws Exception {
                return new ArrayList<T>(session.get(keys).values());
            }
        });
    }

    public static <T, V extends T, P> List<T> getChildren(final Class<T> entityClass, final Class<V> realClass, final P parent) {
        return sessionManager.execute(new SessionManager.Callback<List<T>, Objectify>() {
            public List<T> doInSession(final Objectify session) throws Exception {
                return new ArrayList<T>(session.query(realClass).ancestor(parent).list());
            }
        });
    }
}
