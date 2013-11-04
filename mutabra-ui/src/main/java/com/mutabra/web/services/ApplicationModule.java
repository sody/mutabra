package com.mutabra.web.services;

import org.apache.tapestry5.ComponentParameterConstants;
import org.mongodb.morphia.Datastore;
import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.CodedEntity;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.internal.BattleEncoderFactory;
import com.mutabra.web.internal.ImageSourceImpl;
import com.mutabra.web.internal.MorphiaEncoderFactory;
import com.mutabra.web.internal.NamingObjectProvider;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.*;
import org.apache.tapestry5.services.Core;
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

        configuration.add("mutabra.asset.root", "context:mutabra");
    }

    public static void bind(final ServiceBinder binder) {
        binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withId("MutabraJavaScriptStack");
        binder.bind(ImageSource.class, ImageSourceImpl.class);
    }

    @Contribute(MasterObjectProvider.class)
    public void contributeMasterObjectProvider(final OrderedConfiguration<ObjectProvider> configuration) {
        configuration.add("NamingObjectProvider", new NamingObjectProvider());
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

                                                  @Value("context:WEB-INF/domain")
                                                  final Resource domainMessages) {
        configuration.add("domain", domainMessages);
    }

    @Local
    @Contribute(JavaScriptStack.class)
    public void setupMutabraJavaScriptStack(final OrderedConfiguration<StackExtension> configuration) {
	    configuration.add("mutabra.css", new StackExtension(StackExtensionType.STYLESHEET, "${mutabra.asset.root}/css/mutabra.css"));

	    // project library for tapestry
	    configuration.add("mutabra-init.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/mutabra-init.js"));

	    // jquery library
	    configuration.add("jquery.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/jquery/jquery.js"));
	    // bootstrap plugins
	    configuration.add("bootstrap-transition.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/transition.js"));
	    configuration.add("bootstrap-collapse.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/collapse.js"));
	    configuration.add("bootstrap-dropdown.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/dropdown.js"));
	    configuration.add("bootstrap-tab.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/tab.js"));
	    configuration.add("bootstrap-alert.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/alert.js"));
	    configuration.add("bootstrap-modal.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/modal.js"));
	    configuration.add("bootstrap-carousel.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/carousel.js"));

	    // project plugins
        configuration.add("mutabra-battle.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/mutabra-battle.js"));
    }

    @Contribute(JavaScriptStackSource.class)
    public void contributeJavaScriptStackSource(final MappedConfiguration<String, JavaScriptStack> configuration,
                                                final @Local JavaScriptStack stack) {
        configuration.add("mutabra", stack);
    }
}
