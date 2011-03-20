package com.mutabra.web.services;

import com.mutabra.domain.Translation;
import com.mutabra.domain.common.*;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.Role;
import com.mutabra.services.*;
import com.mutabra.services.common.*;
import com.mutabra.services.player.HeroService;
import com.mutabra.services.player.HeroServiceImpl;
import com.mutabra.services.security.*;
import com.mutabra.web.services.i18n.Translator;
import com.mutabra.web.services.i18n.TranslatorImpl;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.TransactionalAdvice;
import org.greatage.domain.hibernate.HibernateConfiguration;
import org.greatage.domain.hibernate.HibernateExecutor;
import org.greatage.domain.hibernate.HibernateModule;
import org.greatage.ioc.Configuration;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.*;
import org.greatage.ioc.proxy.Interceptor;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency({HibernateModule.class})
public class ServicesModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(TranslationService.class, TranslationServiceImpl.class);
		binder.bind(RoleService.class, RoleServiceImpl.class);
		binder.bind(AccountService.class, AccountServiceImpl.class);
		binder.bind(FaceService.class, FaceServiceImpl.class);
		binder.bind(RaceService.class, RaceServiceImpl.class);
		binder.bind(LevelService.class, LevelServiceImpl.class);
		binder.bind(CardService.class, CardServiceImpl.class);
		binder.bind(HeroService.class, HeroServiceImpl.class);
		binder.bind(Translator.class, TranslatorImpl.class);
	}

	@Build
	public MailService buildMailService() throws NamingException {
		final InitialContext context = new InitialContext();
		final Session session = (Session) context.lookup("mail/Session");
		return new MailServiceImpl(session, "haba.haba.game@gmail.com");
	}

	@Contribute(value = HibernateConfiguration.class, id = "HibernateAnnotationConfiguration")
	public void contributeHibernateAnnotationConfiguration(final Configuration<Class> configuration) {
		configuration.add(Translation.class);
		configuration.add(Account.class);
		configuration.add(Role.class);
		configuration.add(Permission.class);

		configuration.add(Face.class);
		configuration.add(Race.class);
		configuration.add(Level.class);
		configuration.add(Card.class);
		configuration.add(Effect.class);
		configuration.add(Summon.class);

		configuration.add(Hero.class);
		configuration.add(HeroCard.class);
	}

	@Order("Entity")
	@Contribute(value = EntityFilterProcessor.class, id = "HibernateFilterProcessor")
	public void contributeHibernateFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(CodedEntityFilterProcessor.class);
		configuration.addInstance(TranslationFilterProcessor.class);
		configuration.addInstance(AccountFilterProcessor.class);
	}

	@Decorate(BaseEntityService.class)
	@Order("Transaction")
	public Interceptor decorateServices(final HibernateExecutor executor) {
		return new TransactionalAdvice(executor);
	}
}