//package com.mutabra;
//
//import com.mutabra.domain.Translation;
//import com.mutabra.domain.TranslationImpl;
//import com.mutabra.domain.common.Card;
//import com.mutabra.domain.common.CardImpl;
//import com.mutabra.domain.common.Effect;
//import com.mutabra.domain.common.EffectImpl;
//import com.mutabra.domain.common.Face;
//import com.mutabra.domain.common.FaceImpl;
//import com.mutabra.domain.common.Level;
//import com.mutabra.domain.common.LevelImpl;
//import com.mutabra.domain.common.Race;
//import com.mutabra.domain.common.RaceImpl;
//import com.mutabra.domain.common.Summon;
//import com.mutabra.domain.common.SummonImpl;
//import com.mutabra.domain.player.Hero;
//import com.mutabra.domain.player.HeroCard;
//import com.mutabra.domain.player.HeroCardImpl;
//import com.mutabra.domain.player.HeroImpl;
//import com.mutabra.domain.security.Account;
//import com.mutabra.domain.security.AccountImpl;
//import com.mutabra.domain.security.Permission;
//import com.mutabra.domain.security.PermissionImpl;
//import com.mutabra.domain.security.Role;
//import com.mutabra.domain.security.RoleImpl;
//import org.greatage.domain.EntityRepository;
//import org.greatage.domain.hibernate.HibernateConfiguration;
//import org.greatage.domain.hibernate.HibernateModule;
//import org.greatage.inject.Configuration;
//import org.greatage.inject.MappedConfiguration;
//import org.greatage.inject.annotations.Contribute;
//import org.greatage.inject.annotations.Dependency;
//import org.greatage.inject.annotations.Named;
//
///**
// * @author Ivan Khalopik
// * @since 1.0
// */
//@Dependency({HibernateModule.class})
//public class DomainModule {
//
//	@Contribute(HibernateConfiguration.class)
//	@Named("HibernateAnnotationConfiguration")
//	public void contributeHibernateAnnotationConfiguration(final Configuration<Class> configuration) {
//		configuration.add(TranslationImpl.class);
//		configuration.add(AccountImpl.class);
//		configuration.add(RoleImpl.class);
//		configuration.add(PermissionImpl.class);
//
//		configuration.add(FaceImpl.class);
//		configuration.add(RaceImpl.class);
//		configuration.add(LevelImpl.class);
//		configuration.add(CardImpl.class);
//		configuration.add(EffectImpl.class);
//		configuration.add(SummonImpl.class);
//
//		configuration.add(HeroImpl.class);
//		configuration.add(HeroCardImpl.class);
//	}
//
//	@Contribute(EntityRepository.class)
//	public void contributeHibernateRepository(final MappedConfiguration<Class, Class> configuration) {
//		configuration.add(Translation.class, TranslationImpl.class);
//		configuration.add(Account.class, AccountImpl.class);
//		configuration.add(Role.class, RoleImpl.class);
//		configuration.add(Permission.class, PermissionImpl.class);
//
//		configuration.add(Face.class, FaceImpl.class);
//		configuration.add(Race.class, RaceImpl.class);
//		configuration.add(Level.class, LevelImpl.class);
//		configuration.add(Card.class, CardImpl.class);
//		configuration.add(Effect.class, EffectImpl.class);
//		configuration.add(Summon.class, SummonImpl.class);
//
//		configuration.add(Hero.class, HeroImpl.class);
//		configuration.add(HeroCard.class, HeroCardImpl.class);
//	}
//}
