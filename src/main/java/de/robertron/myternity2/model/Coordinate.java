package de.robertron.myternity2.model;

public class Coordinate {

	private final int x;
	private final int y;

	private Coordinate( final int x, final int y ) {
		this.x = x;
		this.y = y;
	}

	public static Coordinate from( final int x, final int y ) {
		return new Coordinate( x, y );
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}