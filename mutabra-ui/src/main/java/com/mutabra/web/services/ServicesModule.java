package com.mutabra.web.services;

import com.mutabra.domain.Translation;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.TranslationQuery;
import com.mutabra.services.security.AccountQuery;
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