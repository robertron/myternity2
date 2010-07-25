package de.robertron.myternity2.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.robertron.myternity2.ga.GaUtil;
import de.robertron.myternity2.ga.Individuum;

public class Board
		implements Individuum<Piece> {

	private final Piece[][] pieces;
	private final int boardSize;
	private int fitness;

	private Board( final Piece[][] pieces, final int boardSize ) {
		this.pieces = pieces;
		this.boardSize = boardSize;
		this.fitness = 0;
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
			result[i] = row;
		}

		return new Board( result, boardSize );
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for ( final Piece[] row : pieces ) {
			for ( final Piece piece : row ) {
				builder.append( piece ).append( "\t" );
			}
			builder.append( "\n" );
		}
		return builder.toString();
	}

	@Override
	public int fitness() {
		return fitness;
	}

	@Override
	public void calculate() {
		int y = 0;
		int fitness = 0;
		for ( final Piece[] row : pieces ) {
			int x = 0;
			for ( final Piece piece : row ) {
				fitness += fitting( piece, getNorth( x, y ), getSouth( x, y ), getEast( x, y ),
						getWest( x, y ) );
				x++;
			}
			y++;
		}

		this.fitness = fitness;

	}

	private int fitting( final Piece piece, final Piece north, final Piece south, final Piece east,
			final Piece west ) {
		final boolean n = check( piece, north, Direction.NORTH );
		final boolean s = check( piece, south, Direction.SOUTH );
		final boolean e = check( piece, east, Direction.EAST );
		final boolean w = check( piece, west, Direction.WEST );

		if ( n && s && e && w ) {
			return 1;
		}
		return 0;
	}

	private boolean check( final Piece piece, final Piece check, final Direction direction ) {
		final int first = piece.get( direction );
		if ( check == null ) {
			return first == 0;
		}
		return first == check.get( direction.getOpposite() );
	}

	private Piece getNorth( final int x, final int y ) {
		if ( y == 0 ) {
			return null;
		}

		return pieces[y - 1][x];
	}

	private Piece getWest( final int x, final int y ) {
		if ( x == boardSize - 1 ) {
			return null;
		}

		return pieces[y][x + 1];
	}

	private Piece getSouth( final int x, final int y ) {
		if ( y == boardSize - 1 ) {
			return null;
		}

		return pieces[y + 1][x];
	}

	private Piece getEast( final int x, final int y ) {
		if ( x == 0 ) {
			return null;
		}

		return pieces[y][x - 1];
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
		final Piece piece = pieces[y1][x1];
		pieces[y1][x1] = pieces[y2][x2];
		pieces[y2][x2] = piece;
	}

	private void rotate() {
		final int x = randomCoordinate();
		final int y = randomCoordinate();
		pieces[y][x].rotate();

	}

	private int randomCoordinate() {
		return (int) GaUtil.random( 0, boardSize - 1 );
	}

	@Override
	public List<Piece> cross( final List<Piece> genes ) {
		final List<Piece> changed = new ArrayList<Piece>();

		for ( final Piece change : genes ) {
			int y = 0;
			for ( final Piece[] row : pieces ) {
				int x = 0;
				for ( final Piece piece : row ) {
					if ( piece.getId() == change.getId() ) {
						changed.add( pieces[y][x] );
						pieces[y][x] = change;
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

	public boolean sane() {
		final Set<Integer> hash = new HashSet<Integer>();
		for ( final Piece[] row : pieces ) {
			for ( final Piece piece : row ) {
				if ( hash.contains( piece.getId() ) ) {
					return false;
				}
			}
		}

		return true;
	}

}
