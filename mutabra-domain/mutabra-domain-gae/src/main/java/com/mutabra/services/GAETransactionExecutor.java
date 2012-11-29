package com.mutabra.services;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Objectify;
import org.greatage.domain.internal.SessionManager;

import java.util.concurrent.Callable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GAETransactionExecutor implements TransactionExecutor {
    private final SessionManager<Objectify> sessionManager;

    public GAETransactionExecutor(final SessionManager<Objectify> sessionManager) {
        this.sessionManager = sessionManager;
    }

    public <V> V doInTransaction(final Callable<V> callback) {
        return sessionManager.execute(new SessionManager.Callback<V, Objectify>() {
            public V doInSession(final Objectify session) throws Exception {
                Transaction transaction = null;
                try {
                    transaction = session.getDatastore().beginTransaction();
                    final V result = callback.call();
                    transaction.commit();
                    return result;
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    if (transaction != null && transaction.isActive()) {
                        transaction.rollback();
                    }
                }
            }
        });
    }
}
