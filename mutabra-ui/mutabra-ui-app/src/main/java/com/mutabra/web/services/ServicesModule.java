package com.mutabra.web.services;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.ChangeSet;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.TranslationService;
import com.mutabra.services.TranslationServiceImpl;
import com.mutabra.web.internal.MailServiceImpl;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Symbol;
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

	public static void bind(final ServiceBinder binder) {
		binder.bind(TranslationService.class, TranslationServiceImpl.class);
	}

	public ServicesModule(final EntityRepository repository) {
		this.repository = repository;
	}

	public MailService buildMailService(final @Symbol("mail.admin-address") String adminAddress) {
		return new MailServiceImpl(adminAddress);
	}

	public BaseEntityService<Role> buildRoleService() {
		return new BaseEntityServiceImpl<Role>(repository, Role.class);
	}

	public BaseEntityService<Permission> buildPermissionService() {
		return new BaseEntityServiceImpl<Permission>(repository, Permission.class);
	}

	public BaseEntityService<Account> buildAccountService() {
		return new BaseEntityServiceImpl<Account>(repository, Account.class);
	}

	public BaseEntityService<ChangeSet> buildChangeSetService() {
		return new BaseEntityServiceImpl<ChangeSet>(repository, ChangeSet.class);
	}

	public BaseEntityService<Level> buildLevelService() {
		return new BaseEntityServiceImpl<Level>(repository, Level.class);
	}

	public BaseEntityService<Face> buildFaceService() {
		return new BaseEntityServiceImpl<Face>(repository, Face.class);
	}

	public BaseEntityService<Race> buildRaceService() {
		return new BaseEntityServiceImpl<Race>(repository, Race.class);
	}

	public BaseEntityService<Hero> buildHeroService() {
		return new BaseEntityServiceImpl<Hero>(repository, Hero.class);
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