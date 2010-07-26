package de.robertron.myternity2.model;

import java.util.HashMap;
import java.util.Map;

import de.robertron.myternity2.ga.Copyable;
import de.robertron.myternity2.ga.Gene;

public class Piece
		implements Gene, Copyable<Piece> {

	private final Map<Direction, Integer> map;
	private final int id;

	private Piece( final int north, final int west, final int south, final int east, final int id ) {
		map = new HashMap<Direction, Integer>();
		this.id = id;
		map.put( Direction.NORTH, north );
		map.put( Direction.EAST, east );
		map.put( Direction.SOUTH, south );
		map.put( Direction.WEST, west );
	}

	public static Piece from( final int north, final int east, final int south, final int west,
			final int index ) {
		return new Piece( north, east, south, west, index );
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append( map.get( Direction.NORTH ) ).append( " " );
		builder.append( map.get( Direction.EAST ) ).append( " " );
		builder.append( map.get( Direction.SOUTH ) ).append( " " );
		builder.append( map.get( Direction.WEST ) );
		return builder.toString();
	}

	public void set( final Direction direction, final int value ) {
		map.put( direction, value );
	}

	public int get( final Direction direction ) {
		return map.get( direction );
	}

	public void rotate() {
		final int north = map.get( Direction.NORTH );
		final int south = map.get( Direction.SOUTH );
		final int east = map.get( Direction.EAST );
		final int west = map.get( Direction.WEST );

		map.put( Direction.EAST, north );
		map.put( Direction.SOUTH, east );
		map.put( Direction.WEST, south );
		map.put( Direction.NORTH, west );
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Piece copy() {
		final int north = map.get( Direction.NORTH );
		final int east = map.get( Direction.EAST );
		final int south = map.get( Direction.SOUTH );
		final int west = map.get( Direction.WEST );
		return new Piece( north, west, south, east, this.id );
	}

}
