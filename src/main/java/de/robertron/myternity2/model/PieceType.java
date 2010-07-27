package de.robertron.myternity2.model;

public enum PieceType {
	CORNER,
	BORDER,
	NORMAL;

	public static PieceType from( final int[] map ) {
		int count = 0;
		for ( final int i : map ) {
			if ( i == 0 ) {
				count++;
			}
		}

		switch ( count ) {
			case 1:
				return BORDER;
			case 2:
				return CORNER;
			default:
				return NORMAL;
		}
	}
}
