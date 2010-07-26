package de.robertron.myternity2.model;

import de.robertron.myternity2.ga.Copyable;
import de.robertron.myternity2.ga.Gene;

public class Piece
		implements Gene, Copyable<Piece>, Cloneable {

	private static final int directionCount = Direction.values().length;

	private final int[] map;
	private final int id;

	private Piece( final int north, final int west, final int south, final int east, final int id ) {
		map = new int[directionCount];
		this.id = id;
		map[Direction.NORTH.ordinal()] = north;
		map[Direction.EAST.ordinal()] = east;
		map[Direction.SOUTH.ordinal()] = south;
		map[Direction.WEST.ordinal()] = west;
	}

	public static Piece from( final int north, final int east, final int south, final int west,
			final int index ) {
		return new Piece( north, east, south, west, index );
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append( get( Direction.NORTH ) ).append( " " );
		builder.append( get( Direction.EAST ) ).append( " " );
		builder.append( get( Direction.SOUTH ) ).append( " " );
		builder.append( get( Direction.WEST ) );
		return builder.toString();
	}

	public void set( final Direction direction, final int value ) {
		map[direction.ordinal()] = value;
	}

	public int get( final Direction direction ) {
		return map[direction.ordinal()];
	}

	public void rotate() {
		final int south = get( Direction.SOUTH );
		set( Direction.SOUTH, get( Direction.EAST ) );
		set( Direction.EAST, get( Direction.NORTH ) );
		set( Direction.NORTH, get( Direction.WEST ) );
		set( Direction.WEST, south );
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Piece copy() {
		try {
			return (Piece) clone();
		} catch ( CloneNotSupportedException e ) {
			throw new RuntimeException( e ); // can't occur
		}
	}

}
