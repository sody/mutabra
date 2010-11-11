package com.noname.web.services.domain;

import org.hibernate.cfg.Configuration;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface HibernateConfiguration {

	void configure(Configuration configuration);

}
