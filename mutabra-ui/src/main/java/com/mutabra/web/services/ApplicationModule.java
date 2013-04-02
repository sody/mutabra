package com.mutabra.web.services;

import com.google.code.morphia.Datastore;
import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.CodedEntity;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.ApplicationConstants;
import com.mutabra.web.internal.BattleEncoderFactory;
import com.mutabra.web.internal.ImageSourceImpl;
import com.mutabra.web.internal.MorphiaEncoderFactory;
import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.*;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.*;
import org.apache.tapestry5.services.messages.ComponentMessagesSource;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationModule {

    @ApplicationDefaults
    @Contribute(SymbolProvider.class)
    public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
        configuration.add(SymbolConstants.START_PAGE_NAME, "/");
        configuration.add(SymbolConstants.EXCEPTION_REPORT_PAGE, "error");
        configuration.add(SymbolConstants.FORM_CLIENT_LOGIC_ENABLED, "false");
        configuration.add(ComponentParameterConstants.ZONE_UPDATE_METHOD, "none");
        configuration.add(ComponentParameterConstants.ZONE_SHOW_METHOD, "none");
    }

    @FactoryDefaults
    @Contribute(SymbolProvider.class)
    public void contributeFactoryDefaults(final MappedConfiguration<String, String> configuration) {
        configuration.add(ApplicationConstants.ROBOT_EMAIL, "${env.robot_email}");
        configuration.add(ApplicationConstants.SUPPORT_EMAIL, "${env.support_email}");
        configuration.add(ApplicationConstants.MONGO_URI, "${env.mongohq_url}");
    }

    public static void bind(final ServiceBinder binder) {
        binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withSimpleId();
        binder.bind(ImageSource.class, ImageSourceImpl.class);
    }

    @Contribute(TypeCoercer.class)
    public void contributeTypeCoercer(final Configuration<CoercionTuple> configuration) {
        configuration.add(CoercionTuple.create(String.class, String[].class, new Coercion<String, String[]>() {
            public String[] coerce(String input) {
                return input != null && !input.isEmpty() ? input.split(",") : null;
            }
        }));
        configuration.add(CoercionTuple.create(String.class, ObjectId.class, new Coercion<String, ObjectId>() {
            public ObjectId coerce(final String input) {
                return new ObjectId(input);
            }
        }));
        configuration.add(CoercionTuple.create(ObjectId.class, String.class, new Coercion<ObjectId, String>() {
            public String coerce(final ObjectId input) {
                return input.toString();
            }
        }));
    }

    @Contribute(ValueEncoderSource.class)
    public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
                                             final TypeCoercer typeCoercer,
                                             final Datastore datastore,
                                             final AccountContext accountContext) {
        configuration.add(BaseEntity.class, new MorphiaEncoderFactory<ObjectId>(typeCoercer, datastore, ObjectId.class));
        configuration.add(CodedEntity.class, new MorphiaEncoderFactory<String>(typeCoercer, datastore, String.class));

        // configure value encoders for battle embedded objects
        final BattleEncoderFactory battleEncoderFactory = new BattleEncoderFactory(accountContext, typeCoercer);
        configuration.add(BattleHero.class, battleEncoderFactory);
        configuration.add(BattleCard.class, battleEncoderFactory);
        configuration.add(BattleCreature.class, battleEncoderFactory);
        configuration.add(BattleAbility.class, battleEncoderFactory);

    }

    @Contribute(ComponentMessagesSource.class)
    public void contributeComponentMessagesSource(final OrderedConfiguration<Resource> configuration,
                                                  @Value("context:WEB-INF/mail") final Resource mailMessages,
                                                  @Value("context:WEB-INF/domain") final Resource domainMessages) {
        configuration.add("mail", mailMessages);
        configuration.add("domain", domainMessages);
    }

    @Local
    @Contribute(JavaScriptStack.class)
    public void contributeJavaScriptStack(final OrderedConfiguration<StackExtension> configuration) {
        configuration.add("mutabra-css", new StackExtension(StackExtensionType.STYLESHEET, "context:css/mutabra.css"));

        // jquery library
        configuration.add("jquery", new StackExtension(StackExtensionType.LIBRARY, "context:js/jquery/jquery.js"));

        // bootstrap plugins
        configuration.add("bootstrap-transition", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-transition.js"));
        configuration.add("bootstrap-dropdown", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-dropdown.js"));
        configuration.add("bootstrap-tab", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-tab.js"));
        configuration.add("bootstrap-alert", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-alert.js"));
        configuration.add("bootstrap-modal", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-modal.js"));
        configuration.add("bootstrap-carousel", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-carousel.js"));

        // project plugins
        configuration.add("mutabra-battle", new StackExtension(StackExtensionType.LIBRARY, "context:js/mutabra/mutabra-battle.js"));

        // project library for tapestry
        configuration.add("mutabra", new StackExtension(StackExtensionType.LIBRARY, "context:js/mutabra-init.js"));
    }

    @Contribute(JavaScriptStackSource.class)
    public void contributeJavaScriptStackSource(final MappedConfiguration<String, JavaScriptStack> configuration,
                                                final @Local JavaScriptStack stack) {
        configuration.add("mutabra", stack);
    }
}
