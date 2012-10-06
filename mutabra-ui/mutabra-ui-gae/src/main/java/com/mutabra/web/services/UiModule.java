package com.mutabra.web.services;

import com.mutabra.domain.BaseEntity;
import com.mutabra.web.internal.GAEEntityEncoderFactory;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.greatage.domain.Repository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SubModule({DomainModule.class, ServicesModule.class, SecurityModule.class, ApplicationModule.class})
public class UiModule {

	@Contribute(ValueEncoderSource.class)
	public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
											 final Repository repository) {
		configuration.add(BaseEntity.class, new GAEEntityEncoderFactory(repository));
	}
}
