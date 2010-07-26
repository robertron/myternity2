package de.robertron.myternity2.model;

import de.robertron.myternity2.ga.Copyable;
import de.robertron.myternity2.ga.Gene;

public class Piece
		implements Gene, Copyable<Piece> {

	private final int[] map;
	private final int id;

	private Piece( final int north, final int east, final int south, final int west, final int id ) {
		this( id, new int[] { north, east, south, west } );
	}

	private Piece( final int id, final int[] map ) {
		this.id = id;
		this.map = map;
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

	public void rotate( final int steps ) {
		for ( int step = 0; step < steps; step++ ) {
			rotate();
		}
	}

	private void rotate() {
		final int i0 = map[0];
		for ( int i = 1; i < map.length; i++ ) {
			map[i - 1] = map[i];
		}
		map[map.length - 1] = i0;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Piece copy() {
		return new Piece( id, map.clone() );
	}

}
