package de.robertron.myternity2.model;

public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;

	public Direction getOpposite() {
		switch ( this ) {
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			case EAST:
				return WEST;
		}
		return null;
	}
}
