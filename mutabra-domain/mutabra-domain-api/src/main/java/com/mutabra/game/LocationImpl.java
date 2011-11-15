package com.mutabra.game;

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

	public FieldLine getLine() {
		return line;
	}

	public int getPosition() {
		return position;
	}
}
