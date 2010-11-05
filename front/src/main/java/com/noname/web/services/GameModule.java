package com.noname.web.services;

import ga.domain.GenericEntity;
import ga.domain.i18n.Translator;
import ga.domain.repository.EntityRepository;
import ga.domain.services.EntityService;
import ga.tapestry.grid.EntityDataSource;
import ga.tapestry.internal.EntityEncoderFactory;
import ga.tapestry.internal.HibernateClassResolver;
import ga.tapestry.internal.I18nPropertyConduitSource;
import ga.tapestry.internal.SpringMessages;
import ga.tapestry.select.EntitySelectModel;
import ga.tapestry.services.ClassResolver;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.*;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.springframework.context.MessageSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SubModule(SecurityModule.class)
public class GameModule {

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
	}

	public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
											 final TypeCoercer typeCoercer,
											 final EntityRepository repository) {
		configuration.add(GenericEntity.class, new EntityEncoderFactory<Long>(typeCoercer, repository, Long.class));
	}

	public PropertyConduitSource decoratePropertyConduitSource(PropertyConduitSource conduitSource,
															   Translator translator,
															   TypeCoercer typeCoercer,
															   ThreadLocale threadLocale,
															   StringInterner interner) {
		return new I18nPropertyConduitSource(translator, typeCoercer, threadLocale, interner, conduitSource);
	}

	public void contributeRealClassResolver(Configuration<ClassResolver> configuration) {
		configuration.addInstance(HibernateClassResolver.class);
	}

	public void contributeTypeCoercer(Configuration<CoercionTuple> configuration,
									  @Builtin final ThreadLocale threadLocale) {
		configuration.add(new CoercionTuple<MessageSource, Messages>(MessageSource.class, Messages.class, new Coercion<MessageSource, Messages>() {
			public Messages coerce(MessageSource input) {
				return new SpringMessages(input, threadLocale.getLocale());
			}
		}));

		configuration.add(new CoercionTuple<EntityService, EntityDataSource>(EntityService.class, EntityDataSource.class, new Coercion<EntityService, EntityDataSource>() {
			@SuppressWarnings({"unchecked"})
			public EntityDataSource coerce(final EntityService entityService) {
				return new EntityDataSource(entityService);
			}
		}));

		configuration.add(new CoercionTuple<EntityService, SelectModel>(EntityService.class, SelectModel.class, new Coercion<EntityService, SelectModel>() {
			@SuppressWarnings({"unchecked"})
			public SelectModel coerce(final EntityService entityService) {
				return new EntitySelectModel(entityService);
			}
		}));
	}
}
