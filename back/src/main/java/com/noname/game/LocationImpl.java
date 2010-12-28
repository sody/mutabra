package com.noname.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocationImpl implements Location {
	private final FieldLine line;
	private final int position;

	public LocationImpl(final FieldLine line, final int position) {
		this.line = line;
		this.position = position;
	}

	@Override
	public FieldLine getLine() {
		return line;
	}

	@Override
	public int getPosition() {
		return position;
	}
}
