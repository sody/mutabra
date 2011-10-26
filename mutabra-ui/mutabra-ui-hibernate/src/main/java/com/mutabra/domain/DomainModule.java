package com.mutabra.domain;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectImpl;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.FaceImpl;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.LevelImpl;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.RaceImpl;
import com.mutabra.domain.common.Summon;
import com.mutabra.domain.common.SummonImpl;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
import com.mutabra.domain.player.HeroCardImpl;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.AccountImpl;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.PermissionImpl;
import com.mutabra.domain.security.Role;
import com.mutabra.domain.security.RoleImpl;
import com.mutabra.services.CodedEntityFilterProcessor;
import com.mutabra.services.TranslationFilterProcessor;
import com.mutabra.services.db.DatabaseService;
import com.mutabra.services.db.DefaultDatabaseService;
import com.mutabra.services.security.AccountFilterProcessor;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.greatage.db.liquibase.LiquibaseChangeLog;
import org.greatage.db.liquibase.LiquibaseDatabase;
import org.greatage.domain.BaseFilterProcessor;
import org.greatage.domain.CompositeFilterProcessor;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.hibernate.HibernateAnnotationConfiguration;
import org.greatage.domain.hibernate.HibernateConfiguration;
import org.greatage.domain.hibernate.HibernateExecutor;
import org.greatage.domain.hibernate.HibernateExecutorImpl;
import org.greatage.domain.hibernate.HibernatePropertyConfiguration;
import org.greatage.domain.hibernate.HibernateRepository;
import org.hibernate.SessionFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DomainModule {
	private static final String PROJECT_DATA_SOURCE = "project.data-source";
	private static final String PROJECT_CHANGE_LOG = "project.change-log";
	private static final String PROJECT_HIBERNATE_DIALECT = "project.hibernate-dialect";

	public static void bind(final ServiceBinder binder) {
		binder.bind(EntityRepository.class, HibernateRepository.class);
		binder.bind(HibernateExecutor.class, HibernateExecutorImpl.class).scope(ScopeConstants.PERTHREAD);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class);

		binder.bind(HibernateAnnotationConfiguration.class).withSimpleId();
		binder.bind(HibernatePropertyConfiguration.class).withSimpleId();
	}

	public DataSource buildDataSource(@Symbol(PROJECT_DATA_SOURCE) final String name) throws NamingException {
		final InitialContext context = new InitialContext();
		return (DataSource) context.lookup("java:comp/env/" + name);
	}

	public DatabaseService buildDatabaseService(final DataSource dataSource,
												@Symbol(PROJECT_CHANGE_LOG) final String changeLog) {
		return new DefaultDatabaseService(new LiquibaseDatabase(dataSource), new LiquibaseChangeLog(changeLog));
	}

	public SessionFactory buildSessionFactory(final List<HibernateConfiguration> configurations) {
		final org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		for (HibernateConfiguration hibernateConfiguration : configurations) {
			hibernateConfiguration.configure(configuration);
		}
		return configuration.buildSessionFactory();
	}

	@Contribute(SessionFactory.class)
	public void contributeSessionFactory(final OrderedConfiguration<HibernateConfiguration> configuration,
										 final HibernatePropertyConfiguration propertyConfiguration,
										 final HibernateAnnotationConfiguration annotationConfiguration) throws IOException {
		configuration.add("Property", propertyConfiguration);
		configuration.add("Annotation", annotationConfiguration, "after:Property");
	}

	@Contribute(EntityFilterProcessor.class)
	public void contributeHibernateFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class);
		configuration.addInstance(CodedEntityFilterProcessor.class);
		configuration.addInstance(TranslationFilterProcessor.class);
		configuration.addInstance(AccountFilterProcessor.class);
	}

	@Contribute(HibernatePropertyConfiguration.class)
	public void contributeHibernateAnnotationConfiguration(final MappedConfiguration<String, String> configuration,
														   @Symbol(PROJECT_DATA_SOURCE) final String name,
														   @Symbol(PROJECT_HIBERNATE_DIALECT) final String dialect) {
		configuration.add("hibernate.connection.datasource", name);
		configuration.add("hibernate.show_sql", "true");
		configuration.add("hibernate.dialect", dialect);
	}

	@Contribute(HibernateAnnotationConfiguration.class)
	public void contributeHibernateAnnotationConfiguration(final Configuration<Class> configuration) {
		configuration.add(TranslationImpl.class);
		configuration.add(AccountImpl.class);
		configuration.add(RoleImpl.class);
		configuration.add(PermissionImpl.class);

		configuration.add(FaceImpl.class);
		configuration.add(RaceImpl.class);
		configuration.add(LevelImpl.class);
		configuration.add(CardImpl.class);
		configuration.add(EffectImpl.class);
		configuration.add(SummonImpl.class);

		configuration.add(HeroImpl.class);
		configuration.add(HeroCardImpl.class);
	}

	@Contribute(EntityRepository.class)
	public void contributeHibernateRepository(final MappedConfiguration<Class, Class> configuration) {
		configuration.add(Translation.class, TranslationImpl.class);

		configuration.add(Account.class, AccountImpl.class);
		configuration.add(Role.class, RoleImpl.class);
		configuration.add(Permission.class, PermissionImpl.class);

		configuration.add(Face.class, FaceImpl.class);
		configuration.add(Race.class, RaceImpl.class);
		configuration.add(Level.class, LevelImpl.class);
		configuration.add(Card.class, CardImpl.class);
		configuration.add(Effect.class, EffectImpl.class);
		configuration.add(Summon.class, SummonImpl.class);

		configuration.add(Hero.class, HeroImpl.class);
		configuration.add(HeroCard.class, HeroCardImpl.class);
	}

	@Startup
	public void updateDatabase(final DatabaseService databaseService,
							   final @Symbol(SymbolConstants.PRODUCTION_MODE) boolean productionMode) {
		if (!productionMode) {
			databaseService.update(false, false);
		}
	}
}
