package com.mutabra.web.services;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.ChangeSet;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.TranslationService;
import com.mutabra.services.TranslationServiceImpl;
import com.mutabra.services.player.HeroService;
import com.mutabra.services.player.HeroServiceImpl;
import com.mutabra.web.internal.MailServiceImpl;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.TransactionCallback;
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
		binder.bind(HeroService.class, HeroServiceImpl.class);
	}

	public ServicesModule(final EntityRepository repository) {
		this.repository = repository;
	}

	public MailService buildMailService(final @Symbol("mail.admin-address") String adminAddress) {
		return new MailServiceImpl(adminAddress);
	}

	public CodedEntityService<Role> buildRoleService() {
		return new CodedEntityServiceImpl<Role>(repository, Role.class);
	}

	public CodedEntityService<Permission> buildPermissionService() {
		return new CodedEntityServiceImpl<Permission>(repository, Permission.class);
	}

	public BaseEntityService<Account> buildAccountService() {
		return new BaseEntityServiceImpl<Account>(repository, Account.class);
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

	@Advise(serviceInterface = BaseEntityService.class)
	public void adviseTransactionalServices(final MethodAdviceReceiver receiver, final TransactionExecutor<Object, Object> executor) {
		final MethodAdvice advice = new MethodAdvice() {
			public void advise(final MethodInvocation invocation) {
				executor.execute(new TransactionCallback<Object, Object>() {
					public Object doInTransaction(final Object transaction) throws Exception {
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
}