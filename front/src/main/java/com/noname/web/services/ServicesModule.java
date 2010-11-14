package com.noname.web.services;

import com.noname.domain.Translation;
import com.noname.domain.common.*;
import com.noname.domain.player.Hero;
import com.noname.domain.player.HeroCard;
import com.noname.domain.security.Account;
import com.noname.domain.security.Permission;
import com.noname.domain.security.Role;
import com.noname.services.*;
import com.noname.services.common.*;
import com.noname.services.player.HeroService;
import com.noname.services.player.HeroServiceImpl;
import com.noname.services.security.AccountFilterProcessor;
import com.noname.services.security.AccountService;
import com.noname.services.security.AccountServiceImpl;
import com.noname.web.services.i18n.Translator;
import com.noname.web.services.i18n.TranslatorImpl;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.TransactionalAdvice;
import org.greatage.domain.hibernate.HibernateConfiguration;
import org.greatage.domain.hibernate.HibernateExecutor;
import org.greatage.domain.hibernate.HibernateModule;
import org.greatage.ioc.Configuration;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.*;
import org.greatage.ioc.proxy.MethodAdvice;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency(HibernateModule.class)
public class ServicesModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(TranslationService.class, TranslationServiceImpl.class);
		binder.bind(AccountService.class, AccountServiceImpl.class);
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

	@Configure(value = HibernateConfiguration.class, serviceId = "HibernateAnnotationConfiguration")
	public void configureHibernateAnnotationConfiguration(final Configuration<Class> configuration) {
		configuration.add(Translation.class);
		configuration.add(Account.class);
		configuration.add(Role.class);
		configuration.add(Permission.class);

		configuration.add(Race.class);
		configuration.add(Level.class);
		configuration.add(Card.class);
		configuration.add(EffectCard.class);
		configuration.add(Effect.class);
		configuration.add(SummonCard.class);
		configuration.add(Summon.class);

		configuration.add(Hero.class);
		configuration.add(HeroCard.class);
	}

	@Configure(value = EntityFilterProcessor.class, serviceId = "HibernateFilterProcessor")
	public void configureHibernateFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(TranslationFilterProcessor.class);
		configuration.addInstance(AccountFilterProcessor.class);
	}

	@Intercept(BaseEntityService.class)
	public MethodAdvice interceptServices(final HibernateExecutor executor) {
		return new TransactionalAdvice(executor);
	}
}