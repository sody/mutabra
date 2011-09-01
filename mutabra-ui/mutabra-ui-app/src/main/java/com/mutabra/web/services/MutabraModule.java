package com.mutabra.web.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SubModule(value = {DomainModule.class, ServicesModule.class})
public class MutabraModule {

	public static void bind(final ServiceBinder binder) {
//		binder.bind(Translator.class, TranslatorImpl.class);
	}

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
	}

//	@Contribute(ValueEncoderSource.class)
//	public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
//											 final TypeCoercer typeCoercer,
//											 final EntityRepository repository) {
//		configuration.add(BaseEntity.class, new EntityEncoderFactory<Long>(typeCoercer, repository, Long.class));
//	}

//	@Decorate(serviceInterface = PropertyConduitSource.class)
//	public PropertyConduitSource decoratePropertyConduitSource(final PropertyConduitSource conduitSource,
//															   final Translator translator,
//															   final StringInterner interner) {
//		return new I18nPropertyConduitSource(conduitSource, translator, interner);
//	}
}
