package de.robertron.myternity2.model;

public class Board {

	private final Piece[][] pieces;

	private Board( final Piece[][] pieces ) {
		this.pieces = pieces;
	}

	public static Board from( final Piece[][] pieces ) {
		return new Board( pieces );
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
