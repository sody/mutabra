package com.mutabra.web.services;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyOpts;
import com.mutabra.db.MutabraChangeLog;
import com.mutabra.domain.Keys;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleImpl;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.FaceImpl;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.LevelImpl;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.RaceImpl;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountImpl;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroImpl;
import com.mutabra.domain.security.ChangeSet;
import com.mutabra.domain.security.ChangeSetImpl;
import com.mutabra.services.GAETransactionExecutor;
import com.mutabra.services.TransactionExecutor;
import com.mutabra.services.db.DatabaseService;
import com.mutabra.services.db.DefaultDatabaseService;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.greatage.db.gae.GAEDatabase;
import org.greatage.domain.Repository;
import org.greatage.domain.internal.SessionManager;
import org.greatage.domain.objectify.ObjectifyRepository;
import org.greatage.domain.objectify.ObjectifySessionManager;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DomainModule {

    public static void bind(final ServiceBinder binder) {
        binder.bind(Repository.class, ObjectifyRepository.class);
        binder.bind(TransactionExecutor.class, GAETransactionExecutor.class);
    }

    public DatabaseService buildDatabaseService() {
        return new DefaultDatabaseService(new GAEDatabase(), new MutabraChangeLog());
    }

    public SessionManager<Objectify> buildObjectifySessionManager() {
        final ObjectifyFactory objectifyFactory = new ObjectifyFactory();

        objectifyFactory.register(ChangeSetImpl.class);

        objectifyFactory.register(FaceImpl.class);
        objectifyFactory.register(RaceImpl.class);
        objectifyFactory.register(LevelImpl.class);
        objectifyFactory.register(CardImpl.class);

        objectifyFactory.register(AccountImpl.class);
        objectifyFactory.register(HeroImpl.class);

        objectifyFactory.register(BattleImpl.class);

        final ObjectifyOpts options = new ObjectifyOpts();
        options.setSessionCache(true);

        return new ObjectifySessionManager(objectifyFactory, options);
    }

    @Contribute(Repository.class)
    public void contributeEntityRepository(final MappedConfiguration<Class, Class> configuration) {
        configuration.add(ChangeSet.class, ChangeSetImpl.class);

        configuration.add(Face.class, FaceImpl.class);
        configuration.add(Race.class, RaceImpl.class);
        configuration.add(Level.class, LevelImpl.class);
        configuration.add(Card.class, CardImpl.class);

        configuration.add(Account.class, AccountImpl.class);
        configuration.add(Hero.class, HeroImpl.class);

        configuration.add(Battle.class, BattleImpl.class);
    }

    @Startup
    public void updateDatabase(final DatabaseService databaseService,
                               final @Symbol(SymbolConstants.PRODUCTION_MODE) boolean productionMode) {
        if (!productionMode) {
            databaseService.update(false, false);
        }
    }

    @Startup
    public void linkSessionManager(final SessionManager<Objectify> sessionManager) {
        Keys.init(sessionManager);
    }
}
