package com.mutabra.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum FieldLine {
	FRONT(3),
	BACK(3);

	private int size;

	FieldLine(final int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}
}
