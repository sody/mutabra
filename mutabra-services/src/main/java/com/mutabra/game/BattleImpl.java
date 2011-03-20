package com.mutabra.game;

import org.greatage.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleImpl implements Battle {
	private final Map<String, BattleCommand> commands = CollectionUtils.newConcurrentMap();

	public BattleImpl(final String... commandNames) {
		this(Arrays.asList(commandNames));
	}

	public BattleImpl(final Collection<String> commandNames) {
		for (String name : commandNames) {
			commands.put(name, new BattleCommandImpl(this, name));
		}
	}

	@Override
	public Collection<BattleCommand> getCommands() {
		return commands.values();
	}

	@Override
	public BattleCommand getCommand(final String name) {
		return commands.get(name);
	}
}
