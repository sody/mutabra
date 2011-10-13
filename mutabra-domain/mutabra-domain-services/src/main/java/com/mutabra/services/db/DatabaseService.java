package com.mutabra.services.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface DatabaseService {

	void update(final boolean dropFirst, final boolean clearCheckSums);
}
