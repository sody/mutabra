package com.noname.web.services;

import com.noname.domain.BaseEntity;
import com.noname.web.pages.security.SignIn;
import com.noname.web.services.i18n.I18nPropertyConduitSource;
import com.noname.web.services.i18n.Translator;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.internal.test.EndOfRequestCleanupFilter;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.services.*;
import org.apache.tapestry5.services.*;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.EntityService;
import org.greatage.tapestry.grid.EntityDataSource;
import org.greatage.tapestry.internal.*;
import org.greatage.tapestry.select.EntitySelectModel;
import org.greatage.tapestry.services.ClassResolver;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GameModule {

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
	}

	public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
											 final TypeCoercer typeCoercer,
											 final EntityRepository repository) {
		configuration.add(BaseEntity.class, new EntityEncoderFactory<Long>(typeCoercer, repository, Long.class));
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

	public void contributeComponentClassTransformWorker(
			final OrderedConfiguration<ComponentClassTransformWorker> configuration) {
		configuration.addInstance("PageSecurity", SecuredAnnotationWorker.class, "before:OnEvent");
	}

	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final ResponseRenderer renderer,
																   final ComponentClassResolver resolver,
																   final Logger logger) {
		return new SecurityExceptionHandler(handler, renderer, resolver, SignIn.class, logger);
	}

	public void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration) {
		configuration.addInstance("ThreadCleanup", ThreadCleanupFilter.class, "before:EndOfRequestCleanup");
		configuration.addInstance("SecurityContextInitializer", UserPersistenceFilter.class);
	}

}
