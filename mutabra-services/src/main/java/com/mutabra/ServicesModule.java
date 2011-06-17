package com.mutabra;

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
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.TransactionalAdvice;
import org.greatage.domain.annotations.Transactional;
import org.greatage.inject.Configuration;
import org.greatage.inject.ServiceAdvice;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Build;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Intercept;
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

		binder.bind(BattleService.class, BattleServiceImpl.class);
	}

	@Build
	public MailService buildMailService() throws NamingException {
		final InitialContext context = new InitialContext();
		final Session session = (Session) context.lookup("mail/Session");
		return new MailServiceImpl(session, "haba.haba.game@gmail.com");
	}

	@Contribute(EntityFilterProcessor.class)
	public void contributeEntityFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(CodedEntityFilterProcessor.class);
		configuration.addInstance(TranslationFilterProcessor.class);
		configuration.addInstance(AccountFilterProcessor.class);
	}

	@Intercept(BaseEntityService.class)
	public void interceptServices(final ServiceAdvice advice) {
		advice.addInstance(TransactionalAdvice.class, "Transaction").annotatedWith(Transactional.class);
		advice.addInstance(AuthoritySecurityAdvice.class, "Allow", "before:Transaction").annotatedWith(Allow.class);
		advice.addInstance(AuthoritySecurityAdvice.class, "Deny", "before:Allow").annotatedWith(Deny.class);
	}
}