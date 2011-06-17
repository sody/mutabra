package com.mutabra;

import com.mutabra.domain.TranslationImpl;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.common.EffectImpl;
import com.mutabra.domain.common.FaceImpl;
import com.mutabra.domain.common.LevelImpl;
import com.mutabra.domain.common.RaceImpl;
import com.mutabra.domain.common.SummonImpl;
import com.mutabra.domain.player.HeroCardImpl;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.AccountImpl;
import com.mutabra.domain.security.PermissionImpl;
import com.mutabra.domain.security.RoleImpl;
import org.greatage.domain.hibernate.HibernateConfiguration;
import org.greatage.inject.Configuration;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Named;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class DomainModule {

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
}
