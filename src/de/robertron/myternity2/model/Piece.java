package de.robertron.myternity2.model;

public class Piece {

	private int north;
	private int west;
	private int south;
	private int east;

	@Override
	public String toString() {
		return super.toString();
	}

	public void setNorth( final int north ) {
		this.north = north;
	}

	public void setEast( final int east ) {
		this.east = east;
	}

	public void setSouth( final int south ) {
		this.south = south;
	}

	public void setWest( final int west ) {
		this.west = west;
	}

}
