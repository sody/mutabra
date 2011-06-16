package com.mutabra.web.services;

import com.mutabra.domain.Translation;
import com.mutabra.domain.TranslationImpl;
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
import com.mutabra.game.BattleService;
import com.mutabra.game.BattleServiceImpl;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.CodedEntityFilterProcessor;
import com.mutabra.services.MailService;
import com.mutabra.services.MailServiceImpl;
import com.mutabra.services.TranslationFilterProcessor;
import com.mutabra.services.TranslationService;
import com.mutabra.services.TranslationServiceImpl;
import com.mutabra.services.common.CardService;
import com.mutabra.services.common.CardServiceImpl;
import com.mutabra.services.common.FaceService;
import com.mutabra.services.common.FaceServiceImpl;
import com.mutabra.services.common.LevelService;
import com.mutabra.services.common.LevelServiceImpl;
import com.mutabra.services.common.RaceService;
import com.mutabra.services.common.RaceServiceImpl;
import com.mutabra.services.player.HeroService;
import com.mutabra.services.player.HeroServiceImpl;
import com.mutabra.services.security.AccountFilterProcessor;
import com.mutabra.services.security.AccountService;
import com.mutabra.services.security.AccountServiceImpl;
import com.mutabra.services.security.RoleService;
import com.mutabra.services.security.RoleServiceImpl;
import com.mutabra.web.services.i18n.Translator;
import com.mutabra.web.services.i18n.TranslatorImpl;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.TransactionalAdvice;
import org.greatage.domain.annotations.Transactional;
import org.greatage.domain.hibernate.HibernateConfiguration;
import org.greatage.domain.hibernate.HibernateModule;
import org.greatage.inject.Configuration;
import org.greatage.inject.MappedConfiguration;
import org.greatage.inject.ServiceAdvice;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Build;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Dependency;
import org.greatage.inject.annotations.Intercept;
import org.greatage.inject.annotations.Named;
import org.greatage.security.AuthoritySecurityAdvice;
import org.greatage.security.annotations.Allow;
import org.greatage.security.annotations.Deny;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency({HibernateModule.class, GameSecurityModule.class})
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

		binder.bind(BattleService.class, BattleServiceImpl.class);
	}

	@Build
	public MailService buildMailService() throws NamingException {
		final InitialContext context = new InitialContext();
		final Session session = (Session) context.lookup("mail/Session");
		return new MailServiceImpl(session, "haba.haba.game@gmail.com");
	}

	@Contribute(HibernateConfiguration.class)
	@Named("HibernateAnnotationConfiguration")
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

	@Contribute(EntityFilterProcessor.class)
	public void contributeHibernateFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(CodedEntityFilterProcessor.class);
		configuration.addInstance(TranslationFilterProcessor.class);
		configuration.addInstance(AccountFilterProcessor.class);
	}

	@Contribute(EntityRepository.class)
	public void contributeHibernateRepository(final MappedConfiguration<Class, Class> configuration) {
		configuration.add(Translation.class, TranslatorImpl.class);
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

	@Intercept(BaseEntityService.class)
	public void interceptServices(final ServiceAdvice advice) {
		advice.addInstance(TransactionalAdvice.class, "Transaction").annotatedWith(Transactional.class);
		advice.addInstance(AuthoritySecurityAdvice.class, "Allow", "before:Transaction").annotatedWith(Allow.class);
		advice.addInstance(AuthoritySecurityAdvice.class, "Deny", "before:Allow").annotatedWith(Deny.class);
	}
}