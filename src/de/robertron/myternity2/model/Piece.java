package de.robertron.myternity2.model;

import java.util.HashMap;
import java.util.Map;

import de.robertron.myternity2.ga.Gene;

public class Piece
		implements Gene {

	private final Map<Direction, Integer> map = new HashMap<Direction, Integer>();
	private final int id;

	private Piece( final int north, final int east, final int south, final int west, final int id ) {
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

	}

	@Override
	public int getId() {
		return this.id;
	}

}
