package com.mutabra.web.services;

import com.mutabra.annotations.Transactional;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.security.ChangeSet;
import com.mutabra.scripts.AttackScript;
import com.mutabra.scripts.EffectScript;
import com.mutabra.scripts.FakeScript;
import com.mutabra.scripts.ScriptExecutor;
import com.mutabra.scripts.ScriptExecutorImpl;
import com.mutabra.scripts.SummonScript;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.TransactionExecutor;
import com.mutabra.services.TranslationService;
import com.mutabra.services.TranslationServiceImpl;
import com.mutabra.services.battle.BattleService;
import com.mutabra.services.battle.BattleServiceImpl;
import com.mutabra.services.game.HeroService;
import com.mutabra.services.game.HeroServiceImpl;
import com.mutabra.web.SecurityConstants;
import com.mutabra.web.internal.MailServiceImpl;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.greatage.domain.Repository;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServicesModule {
	private final Repository repository;

	public static void bind(final ServiceBinder binder) {
		binder.bind(TranslationService.class, TranslationServiceImpl.class);
		binder.bind(BattleService.class, BattleServiceImpl.class);
		binder.bind(ScriptExecutor.class, ScriptExecutorImpl.class);
	}

	public ServicesModule(final Repository repository) {
		this.repository = repository;
	}

	public MailService buildMailService(final @Symbol(SecurityConstants.ROBOT_EMAIL) String robotEmail) {
		return new MailServiceImpl(robotEmail);
	}

	public BaseEntityService<ChangeSet> buildChangeSetService() {
		return new BaseEntityServiceImpl<ChangeSet>(repository, ChangeSet.class);
	}

	public CodedEntityService<Level> buildLevelService() {
		return new CodedEntityServiceImpl<Level>(repository, Level.class);
	}

	public CodedEntityService<Face> buildFaceService() {
		return new CodedEntityServiceImpl<Face>(repository, Face.class);
	}

	public CodedEntityService<Race> buildRaceService() {
		return new CodedEntityServiceImpl<Race>(repository, Race.class);
	}

	public CodedEntityService<Card> buildCardService() {
		return new CodedEntityServiceImpl<Card>(repository, Card.class);
	}

	public BaseEntityService<Account> buildAccountService() {
		return new BaseEntityServiceImpl<Account>(repository, Account.class);
	}

	public HeroService buildHeroService(final @InjectService("levelService") CodedEntityService<Level> levelService,
										final @InjectService("cardService") CodedEntityService<Card> cardService) {
		return new HeroServiceImpl(repository, levelService, cardService);
	}

	@Advise(serviceInterface = BaseEntityService.class)
	public void adviseTransactionalServices(final MethodAdviceReceiver receiver,
											final TransactionExecutor transactionExecutor) {
		final MethodAdvice advice = new MethodAdvice() {
			public void advise(final MethodInvocation invocation) {
				transactionExecutor.doInTransaction(new Callable<Object>() {
					public Object call() throws Exception {
						invocation.proceed();
						return invocation.getReturnValue();
					}
				});
			}
		};

		for (Method method : receiver.getInterface().getMethods()) {
			if (receiver.getMethodAnnotation(method, Transactional.class) != null) {
				receiver.adviseMethod(method, advice);
			}
		}
	}

	@Contribute(ScriptExecutor.class)
	public void contributeScriptExecutor(final Configuration<EffectScript> configuration) {
		configuration.addInstance(FakeScript.class);
		configuration.addInstance(AttackScript.class);
		configuration.addInstance(SummonScript.class);
	}
}