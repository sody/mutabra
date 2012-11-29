package com.mutabra.web.components.game;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleDisplay {

    @Inject
    private AccountContext accountContext;

    @Inject
    private BattleService battleService;

    @Property
    private Battle battle;

    @Property
    private BattleHero you;

    @Property
    private BattleHero opponent;

    @Property
    private BattleField field;

    @Property
    private List<BattleField> fields;

    @Property
    private Card card;

    @Property
    private Ability ability;

    public String getActionsClass() {
        return field.hasHero() && !field.isEnemySide() ?
                "row actions active" :
                "row actions";
    }

    @SetupRender
    void setupBattleField() {
        final Hero hero = accountContext.getHero();
        battle = accountContext.getBattle();

        fields = battleService.getBattleField(hero, battle);
        for (BattleField battleField : fields) {
            if (battleField.hasHero()) {
                if (battleField.isEnemySide()) {
                    opponent = battleField.getHero();
                } else {
                    you = battleField.getHero();
                }
            }
        }
    }
}
