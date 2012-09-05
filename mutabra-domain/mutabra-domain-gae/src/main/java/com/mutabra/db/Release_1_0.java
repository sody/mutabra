package com.mutabra.db;

import com.mutabra.domain.Translations;
import com.mutabra.domain.common.Abilities;
import com.mutabra.domain.common.Cards;
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.Levels;
import com.mutabra.domain.common.Races;
import com.mutabra.domain.common.TargetType;
import com.mutabra.domain.game.Role;
import com.mutabra.scripts.AttackScript;
import com.mutabra.scripts.SummonScript;
import org.greatage.db.ChangeLog;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Release_1_0 extends ChangeLog {

	@Override
	protected void init() {
		location("mutabra/release_1.0");

		author("sody");

		begin("2011-10-11/roles").comment("roles data");
		insert(Tables.TRANSLATION)
				.set("type", Tables.ROLE)
				.into("code", "locale", "variant", "value")
				.values(Role.ADMIN.getTranslationCode(), Translations.DEFAULT, Translations.NAME, "Administrator")
				.values(Role.ADMIN.getTranslationCode(), Translations.RUSSIAN, Translations.NAME, "Администратор")
				.values(Role.USER.getTranslationCode(), Translations.DEFAULT, Translations.NAME, "User")
				.values(Role.USER.getTranslationCode(), Translations.RUSSIAN, Translations.NAME, "Пользователь");

		begin("2011-10-11/levels").comment("levels data");
		insert(Tables.LEVEL)
				.into("code", "rating")
				.values(Levels.NEWBIE, 10l);
		insert(Tables.TRANSLATION)
				.set("type", Tables.LEVEL)
				.into("code", "locale", "variant", "value")
				.values(Levels.NEWBIE, Translations.DEFAULT, Translations.NAME, "Newbie")
				.values(Levels.NEWBIE, Translations.RUSSIAN, Translations.NAME, "Нубик");

		begin("2011-10-11/faces").comment("faces data");
		insert(Tables.FACE)
				.into("code")
				.values("f1")
				.values("f2");

		begin("2011-10-11/races").comment("races data");
		insert(Tables.RACE)
				.into("code")
				.values(Races.PLUNGER)
				.values(Races.FLYER);
		insert(Tables.TRANSLATION)
				.set("type", Tables.RACE)
				.into("code", "locale", "variant", "value")
				.values(Races.PLUNGER, Translations.DEFAULT, Translations.NAME, "Plunger")
				.values(Races.PLUNGER, Translations.RUSSIAN, Translations.NAME, "Ныряльщик")
				.values(Races.PLUNGER, Translations.DEFAULT, Translations.DESCRIPTION, "Plungers is a race that comes from the sunny world where there is nothing except water...")
				.values(Races.PLUNGER, Translations.RUSSIAN, Translations.DESCRIPTION, "Ныряльщики это гордая раса, происходящая из солнечного мира, где нет ничего, кроме Великого Океана...")
				.values(Races.FLYER, Translations.DEFAULT, Translations.NAME, "Flyer")
				.values(Races.FLYER, Translations.RUSSIAN, Translations.NAME, "Летун")
				.values(Races.FLYER, Translations.DEFAULT, Translations.DESCRIPTION, "Flyers is a race that comes from the world of one mountain...")
				.values(Races.FLYER, Translations.RUSSIAN, Translations.DESCRIPTION, "Летуны это создания живущие в мире одной горы, которую величают Мать-Гора...");

		begin("2011-11-23/plunger_and_flyer_cards").comment("plunger and flyer cards added");
		insert(Tables.CARD)
				.set("duration", 1)
				.set("targetType", TargetType.SINGLE_FRIEND_EMPTY.name())
				.into("code", "bloodCost", "power", "health")
				.values(Cards.ELECTRIC_RAY, 2, 3, 5)
				.values(Cards.SEAHORSE, 2, 1, 3)
				.values(Cards.MERMAID, 2, 0, 4)
				.values(Cards.CHAMOIS, 2, 2, 6)
				.values(Cards.CARRION_VULTURE, 2, 2, 5)
				.values(Cards.CHIVES, 2, 0, 1);
		//todo: add effects
		insert(Tables.CARD)
				.set("health", 0)
				.into("code", "targetType", "bloodCost", "power", "duration")
				.values(Cards.CALM, TargetType.ALL_FRIEND_UNIT.name(), 1, 1, 3)
				.values(Cards.WAVE, TargetType.SINGLE_ENEMY_UNIT.name(), 2, 4, 1)
				.values(Cards.WHIRLPOOL, TargetType.SINGLE_ENEMY_EMPTY.name(), 1, 0, 1)
				.values(Cards.TRIDENT_BLOW, TargetType.SINGLE_ENEMY_UNIT.name(), 2, 6, 1)
				.values(Cards.SWIM_AWAY, TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 0, 1)
				.values(Cards.STORM, TargetType.ALL_FRIEND_UNIT.name(), 2, 0, 1)
				.values(Cards.DROP_OF_THE_OCEAN, TargetType.SINGLE_ENEMY_UNIT.name(), 2, 0, 2)
				.values(Cards.SCRAMBLE, TargetType.SINGLE_FRIEND_HERO.name(), 1, 0, 1)
				.values(Cards.SCRATCH, TargetType.SINGLE_ENEMY_HERO.name(), 1, 5, 1)
				.values(Cards.SNOWBALL, TargetType.ALL_ENEMY_UNIT.name(), 1, 2, 1)
				.values(Cards.THROW, TargetType.SINGLE_ENEMY_UNIT.name(), 1, 3, 1)
				.values(Cards.FALLING_BOULDER, TargetType.SINGLE_ENEMY_UNIT.name(), 1, 7, 1)
				.values(Cards.ECHO_MOUNTAIN, TargetType.ALL_UNIT.name(), 2, 4, 1)
				.values(Cards.DECOMPRESSION, TargetType.ALL_UNIT.name(), 1, -1, 2);
		insert(Tables.TRANSLATION)
				.set("type", Tables.CARD)
				.into("code", "locale", "variant", "value")
				.values(Cards.ELECTRIC_RAY, Translations.DEFAULT, Translations.NAME, "Electric Ray")
				.values(Cards.ELECTRIC_RAY, Translations.RUSSIAN, Translations.NAME, "Электрический Скат")
				.values(Cards.SEAHORSE, Translations.DEFAULT, Translations.NAME, "Seahorse")
				.values(Cards.SEAHORSE, Translations.RUSSIAN, Translations.NAME, "Морской Конек")
				.values(Cards.MERMAID, Translations.DEFAULT, Translations.NAME, "Mermaid")
				.values(Cards.MERMAID, Translations.RUSSIAN, Translations.NAME, "Русалка")
				.values(Cards.CALM, Translations.DEFAULT, Translations.NAME, "Calm")
				.values(Cards.CALM, Translations.RUSSIAN, Translations.NAME, "Штиль")
				.values(Cards.WAVE, Translations.DEFAULT, Translations.NAME, "Wave")
				.values(Cards.WAVE, Translations.RUSSIAN, Translations.NAME, "Волна")
				.values(Cards.WHIRLPOOL, Translations.DEFAULT, Translations.NAME, "Whirlpool")
				.values(Cards.WHIRLPOOL, Translations.RUSSIAN, Translations.NAME, "Водоворот")
				.values(Cards.TRIDENT_BLOW, Translations.DEFAULT, Translations.NAME, "Trident Blow")
				.values(Cards.TRIDENT_BLOW, Translations.RUSSIAN, Translations.NAME, "Удар трезубцем")
				.values(Cards.SWIM_AWAY, Translations.DEFAULT, Translations.NAME, "Swim Away")
				.values(Cards.SWIM_AWAY, Translations.RUSSIAN, Translations.NAME, "Уплыть")
				.values(Cards.STORM, Translations.DEFAULT, Translations.NAME, "Storm")
				.values(Cards.STORM, Translations.RUSSIAN, Translations.NAME, "Шторм")
				.values(Cards.DROP_OF_THE_OCEAN, Translations.DEFAULT, Translations.NAME, "Drop of the Ocean")
				.values(Cards.DROP_OF_THE_OCEAN, Translations.RUSSIAN, Translations.NAME, "Капля океана")
				.values(Cards.CHAMOIS, Translations.DEFAULT, Translations.NAME, "Chamois")
				.values(Cards.CHAMOIS, Translations.RUSSIAN, Translations.NAME, "Серна")
				.values(Cards.CARRION_VULTURE, Translations.DEFAULT, Translations.NAME, "Carrion Vulture")
				.values(Cards.CARRION_VULTURE, Translations.RUSSIAN, Translations.NAME, "Гриф Падальщик")
				.values(Cards.CHIVES, Translations.DEFAULT, Translations.NAME, "Chives")
				.values(Cards.CHIVES, Translations.RUSSIAN, Translations.NAME, "Лук Скорода")
				.values(Cards.SCRAMBLE, Translations.DEFAULT, Translations.NAME, "Scramble")
				.values(Cards.SCRAMBLE, Translations.RUSSIAN, Translations.NAME, "Вскарабкаться")
				.values(Cards.SCRATCH, Translations.DEFAULT, Translations.NAME, "Scratch")
				.values(Cards.SCRATCH, Translations.RUSSIAN, Translations.NAME, "Поцарапать")
				.values(Cards.SNOWBALL, Translations.DEFAULT, Translations.NAME, "Snowball")
				.values(Cards.SNOWBALL, Translations.RUSSIAN, Translations.NAME, "Снежный Ком")
				.values(Cards.THROW, Translations.DEFAULT, Translations.NAME, "Throw")
				.values(Cards.THROW, Translations.RUSSIAN, Translations.NAME, "Сбросить")
				.values(Cards.FALLING_BOULDER, Translations.DEFAULT, Translations.NAME, "Falling Boulder")
				.values(Cards.FALLING_BOULDER, Translations.RUSSIAN, Translations.NAME, "Падение валуна")
				.values(Cards.ECHO_MOUNTAIN, Translations.DEFAULT, Translations.NAME, "Echo Mountain")
				.values(Cards.ECHO_MOUNTAIN, Translations.RUSSIAN, Translations.NAME, "Эхо горы")
				.values(Cards.DECOMPRESSION, Translations.DEFAULT, Translations.NAME, "Decompression")
				.values(Cards.DECOMPRESSION, Translations.RUSSIAN, Translations.NAME, "Понижение давления");

		insert(Tables.ABILITY)
				.set("health", 0)
				.into("__parent__", "code", "targetType", "bloodCost", "power", "duration")
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.ELECTRIC_RAY)),
						Abilities.ELECTRIC_RAY_ATTACK, TargetType.SINGLE_ENEMY_UNIT.name(), 0, 3, 1
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.CHAMOIS)),
						Abilities.CHAMOIS_ATTACK, TargetType.SINGLE_ENEMY_UNIT.name(), 0, 2, 1
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.CARRION_VULTURE)),
						Abilities.CARRION_VULTURE_ATTACK, TargetType.SINGLE_ENEMY_UNIT.name(), 0, 2, 1
				);
		insert(Tables.TRANSLATION)
				.set("type", Tables.ABILITY)
				.into("code", "locale", "variant", "value")
				.values(Abilities.ELECTRIC_RAY_ATTACK, Translations.DEFAULT, Translations.NAME, "Attack")
				.values(Abilities.ELECTRIC_RAY_ATTACK, Translations.RUSSIAN, Translations.NAME, "Атака")
				.values(Abilities.CHAMOIS_ATTACK, Translations.DEFAULT, Translations.NAME, "Attack")
				.values(Abilities.CHAMOIS_ATTACK, Translations.RUSSIAN, Translations.NAME, "Атака")
				.values(Abilities.CARRION_VULTURE_ATTACK, Translations.DEFAULT, Translations.NAME, "Attack")
				.values(Abilities.CARRION_VULTURE_ATTACK, Translations.RUSSIAN, Translations.NAME, "Атака");

		insert(Tables.EFFECT)
				.into("__parent__", "scriptClass", "effectType", "targetType", "power", "duration", "health")
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.ELECTRIC_RAY)),
						SummonScript.class.getName(), EffectType.SUMMON.name(), TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 3, 5
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.SEAHORSE)),
						SummonScript.class.getName(), EffectType.SUMMON.name(), TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 0, 3
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.MERMAID)),
						SummonScript.class.getName(), EffectType.SUMMON.name(), TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 0, 4
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.CHAMOIS)),
						SummonScript.class.getName(), EffectType.SUMMON.name(), TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 2, 6
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.CARRION_VULTURE)),
						SummonScript.class.getName(), EffectType.SUMMON.name(), TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 2, 5
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.CHIVES)),
						SummonScript.class.getName(), EffectType.SUMMON.name(), TargetType.SINGLE_FRIEND_EMPTY.name(), 0, 0, 1
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.WAVE)),
						AttackScript.class.getName(), EffectType.MAGIC_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 4, 1, 0
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.TRIDENT_BLOW)),
						AttackScript.class.getName(), EffectType.MELEE_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 6, 1, 0
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.SCRATCH)),
						AttackScript.class.getName(), EffectType.MELEE_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 5, 1, 0
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.SNOWBALL)),
						AttackScript.class.getName(), EffectType.MAGIC_ATTACK.name(), TargetType.ALL_ENEMY_UNIT.name(), 2, 1, 0
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.THROW)),
						AttackScript.class.getName(), EffectType.RANGED_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 3, 1, 0
				)
				.values(
						select(Tables.CARD).where(condition("code").equal(Cards.FALLING_BOULDER)),
						AttackScript.class.getName(), EffectType.RANGED_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 7, 1, 0
				);

		insert(Tables.EFFECT)
				.into("__parent__", "scriptClass", "effectType", "targetType", "power", "duration", "health")
				.values(
						select(Tables.ABILITY).where(condition("code").equal(Abilities.ELECTRIC_RAY_ATTACK)),
						AttackScript.class.getName(), EffectType.MAGIC_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 3, 1, 0
				)
				.values(
						select(Tables.ABILITY).where(condition("code").equal(Abilities.CHAMOIS_ATTACK)),
						AttackScript.class.getName(), EffectType.MAGIC_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 2, 1, 0
				)
				.values(
						select(Tables.ABILITY).where(condition("code").equal(Abilities.CARRION_VULTURE_ATTACK)),
						AttackScript.class.getName(), EffectType.MAGIC_ATTACK.name(), TargetType.SINGLE_ENEMY_UNIT.name(), 2, 1, 0
				);
	}
}
