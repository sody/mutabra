package com.mutabra.web.services;

import com.mutabra.domain.Translation;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.ChangeSet;
import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.TranslationQuery;
import com.mutabra.services.common.FaceQuery;
import com.mutabra.services.common.LevelQuery;
import com.mutabra.services.common.RaceQuery;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.services.security.ChangeSetQuery;
import com.mutabra.services.security.RoleQuery;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.Transaction;
import org.greatage.domain.TransactionExecutor;
import org.greatage.domain.annotations.Transactional;

import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServicesModule {
	private final EntityRepository repository;

	public ServicesModule(final EntityRepository repository) {
		this.repository = repository;
	}

	public BaseEntityService<Translation, TranslationQuery> buildTranslationService() {
		return new BaseEntityServiceImpl<Translation, TranslationQuery>(repository, Translation.class, TranslationQuery.class);
	}

	public BaseEntityService<Role, RoleQuery> buildRoleService() {
		return new BaseEntityServiceImpl<Role, RoleQuery>(repository, Role.class, RoleQuery.class);
	}

	public BaseEntityService<Account, AccountQuery> buildAccountService() {
		return new BaseEntityServiceImpl<Account, AccountQuery>(repository, Account.class, AccountQuery.class);
	}

	public BaseEntityService<ChangeSet, ChangeSetQuery> buildChangeSetService() {
		return new BaseEntityServiceImpl<ChangeSet, ChangeSetQuery>(repository, ChangeSet.class, ChangeSetQuery.class);
	}

	public BaseEntityService<Level, LevelQuery> buildLevelService() {
		return new BaseEntityServiceImpl<Level, LevelQuery>(repository, Level.class, LevelQuery.class);
	}

	public BaseEntityService<Face, FaceQuery> buildFaceService() {
		return new BaseEntityServiceImpl<Face, FaceQuery>(repository, Face.class, FaceQuery.class);
	}

	public BaseEntityService<Race, RaceQuery> buildRaceService() {
		return new BaseEntityServiceImpl<Race, RaceQuery>(repository, Race.class, RaceQuery.class);
	}

	@Advise(serviceInterface = BaseEntityService.class)
	public void adviseTransactionalServices(final MethodAdviceReceiver receiver, final TransactionExecutor executor) {
		final MethodAdvice advice = new MethodAdvice() {
			public void advise(MethodInvocation invocation) {
				final Transaction transaction = executor.begin();
				try {
					invocation.proceed();
					transaction.commit();
				} catch (Throwable throwable) {
					transaction.rollback();
					throw new RuntimeException("Could not finish transaction.", throwable);
				}
			}
		};

		for (Method method : receiver.getInterface().getMethods()) {
			if (receiver.getMethodAnnotation(method, Transactional.class) != null) {
				receiver.adviseMethod(method, advice);
			}
		}
	}
}