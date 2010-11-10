package com.noname.web.services;

import com.noname.domain.Translation;
import com.noname.domain.common.*;
import com.noname.domain.player.Hero;
import com.noname.domain.player.HeroCard;
import com.noname.domain.security.Account;
import com.noname.domain.security.Permission;
import com.noname.domain.security.Role;
import com.noname.services.BaseEntityService;
import com.noname.services.TranslationFilterProcessor;
import com.noname.services.TranslationService;
import com.noname.services.TranslationServiceImpl;
import com.noname.services.common.CardService;
import com.noname.services.common.CardServiceImpl;
import com.noname.services.common.LevelService;
import com.noname.services.common.LevelServiceImpl;
import com.noname.services.player.HeroService;
import com.noname.services.player.HeroServiceImpl;
import com.noname.services.security.AccountService;
import com.noname.services.security.AccountServiceImpl;
import org.greatage.domain.*;
import org.greatage.domain.hibernate.HibernateExecutor;
import org.greatage.domain.hibernate.HibernateExecutorImpl;
import org.greatage.domain.hibernate.HibernateRepository;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Configure;
import org.greatage.ioc.annotations.Intercept;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.ioc.resource.Resource;
import org.greatage.ioc.resource.ResourceLocator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServicesModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(HibernateExecutor.class, HibernateExecutorImpl.class).withScope(ScopeConstants.THREAD);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class).withScope(ScopeConstants.GLOBAL);
		binder.bind(EntityRepository.class, HibernateRepository.class).withScope(ScopeConstants.GLOBAL);

		binder.bind(TranslationService.class, TranslationServiceImpl.class);
		binder.bind(AccountService.class, AccountServiceImpl.class);
		binder.bind(LevelService.class, LevelServiceImpl.class);
		binder.bind(CardService.class, CardServiceImpl.class);
		binder.bind(HeroService.class, HeroServiceImpl.class);
		binder.bind(Translator.class, TranslatorImpl.class);
	}

	@Build
	public SessionFactory buildSessionFactory(final List<HibernateConfiguration> configurations) {
		final Configuration configuration = new Configuration();
		for (HibernateConfiguration hibernateConfiguration : configurations) {
			hibernateConfiguration.configure(configuration);
		}
		return configuration.buildSessionFactory();
	}

	@Configure(SessionFactory.class)
	public void configureSessionFactory(final OrderedConfiguration<HibernateConfiguration> configuration, final ResourceLocator locator) throws IOException {
		configuration.add(new HibernateConfiguration() {
			@Override
			public void configure(final Configuration configuration) {
				final Resource resource = locator.getResource("hibernate.properties");
				final Properties properties = new Properties();
				try {
					properties.load(resource.open());
					configuration.setProperties(properties);
				} catch (IOException e) {
					throw new RuntimeException("Can not find hibernate.properties", e);
				}
			}
		}, "Property");
		configuration.add(new HibernateConfiguration() {
			@Override
			public void configure(final Configuration configuration) {
				configuration.addAnnotatedClass(Translation.class);
				configuration.addAnnotatedClass(Account.class);
				configuration.addAnnotatedClass(Role.class);
				configuration.addAnnotatedClass(Permission.class);

				configuration.addAnnotatedClass(Level.class);
				configuration.addAnnotatedClass(Card.class);
				configuration.addAnnotatedClass(EffectCard.class);
				configuration.addAnnotatedClass(Effect.class);
				configuration.addAnnotatedClass(SummonCard.class);
				configuration.addAnnotatedClass(Summon.class);

				configuration.addAnnotatedClass(Hero.class);
				configuration.addAnnotatedClass(HeroCard.class);
			}
		}, "Annotation", "after:Property");
	}

	@Configure(EntityFilterProcessor.class)
	public void configureEntityFilterProcessor(final OrderedConfiguration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class, "Base");
		configuration.addInstance(TranslationFilterProcessor.class, "Translation");
	}

	@Intercept(BaseEntityService.class)
	public MethodAdvice interceptServices(final HibernateExecutor executor) {
		return new TransactionalAdvice(executor);
	}
}