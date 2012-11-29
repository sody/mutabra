package com.mutabra.services;

import java.util.concurrent.Callable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TransactionExecutor {

    <V> V doInTransaction(Callable<V> callback);
}
