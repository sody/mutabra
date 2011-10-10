package org.greatage.db;

import org.greatage.domain.annotations.Transactional;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface DatabaseService {

	@Transactional
	void update();
}
