package de.robertron.myternity2.model;

import java.util.ArrayList;
import java.util.List;

import de.robertron.myternity2.ga.GaUtil;
import de.robertron.myternity2.ga.Individuum;

public class Board
		implements Individuum<Piece> {

	private final Piece[][] pieces;
	private final int boardSize;

	private Board( final Piece[][] pieces, final int boardSize ) {
		this.pieces = pieces;
		this.boardSize = boardSize;
	}

	public static Board from( final Piece[][] pieces, final int boardSize ) {
		return new Board( pieces, boardSize );
	}

	public static Board from( final List<Piece> pieces, final int boardSize ) {
		final Piece[][] result = new Piece[boardSize][];

		int count = 0;
		for ( int i = 0; i < boardSize; i++ ) {
			final Piece[] row = new Piece[boardSize];
			for ( int j = 0; j < boardSize; j++ ) {
				row[j] = pieces.get( count++ );
			}
		}

		return new Board( result, boardSize );
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public int getFitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void mutate( final double probability ) {
		if ( Math.random() >= probability )
			return;

		do {
			if ( Math.random() > 0.5 ) {
				changePositions();
			} else {
				rotate();
			}

		} while ( Math.random() < probability );
	}

	private void changePositions() {
		final int x1 = randomCoordinate();
		final int y1 = randomCoordinate();
		final int x2 = randomCoordinate();
		final int y2 = randomCoordinate();
		final Piece piece = pieces[x1][y1];
		pieces[x1][y1] = pieces[x2][y2];
		pieces[x2][y2] = piece;
	}

	private void rotate() {
		final int x = randomCoordinate();
		final int y = randomCoordinate();
		pieces[x][y].rotate();

	}

	private int randomCoordinate() {
		return (int) GaUtil.random( 0, boardSize - 1 );
	}

	@Override
	public List<Piece> cross( final List<Piece> genes ) {
		final List<Piece> changed = new ArrayList<Piece>();

		for ( final Piece change : genes ) {
			int x = 0;
			int y = 0;
			for ( final Piece[] row : pieces ) {
				for ( final Piece piece : row ) {
					if ( piece.getId() == change.getId() ) {
						changed.add( pieces[x][y] );
						pieces[x][y] = change;
					}
					x++;
				}
				y++;
			}
		}
		return changed;
	}

	@Override
	public List<Piece> getGenes() {
		return Converter.convert( pieces );
	}

}
