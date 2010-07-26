package de.robertron.myternity2.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.robertron.myternity2.config.Configuration;
import de.robertron.myternity2.config.Key;
import de.robertron.myternity2.ga.Copyable;
import de.robertron.myternity2.ga.GaUtil;
import de.robertron.myternity2.ga.Individuum;

public class Board
		implements Individuum<Piece>, Copyable<Board> {

	private final Piece[][] pieces;
	private final int boardSize;
	private int fitness;

	private Board( final Piece[][] pieces, final int boardSize, final int fitness ) {
		this.pieces = pieces;
		this.boardSize = boardSize;
		this.fitness = fitness;
	}

	public static Board from( final Piece[][] pieces, final int boardSize ) {
		return new Board( pieces, boardSize, 0 );
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

		return new Board( result, boardSize, 0 ).copy();
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
		final int n = check( piece, north, Direction.NORTH );
		final int s = check( piece, south, Direction.SOUTH );
		final int e = check( piece, east, Direction.EAST );
		final int w = check( piece, west, Direction.WEST );

		return n + s + e + w;
	}

	private int check( final Piece piece, final Piece check, final Direction direction ) {
		final int first = piece.get( direction );
		if ( check == null ) {
			if ( first == 0 ) {
				return Configuration.getInt( Key.FITNESS_REWARDEDGE );
			}
			return 0;
		}

		if ( first == check.get( direction.getOpposite() ) ) {
			return Configuration.getInt( Key.FITNESS_REWARD );
		}

		return 0;
	}

	private Piece getNorth( final int x, final int y ) {
		if ( y == 0 ) {
			return null;
		}

		return pieces[y - 1][x];
	}

	private Piece getWest( final int x, final int y ) {
		if ( x == 0 ) {
			return null;
		}

		return pieces[y][x - 1];
	}

	private Piece getSouth( final int x, final int y ) {
		if ( y == boardSize - 1 ) {
			return null;
		}

		return pieces[y + 1][x];
	}

	private Piece getEast( final int x, final int y ) {
		if ( x == boardSize - 1 ) {
			return null;
		}

		return pieces[y][x + 1];
	}

	@Override
	public int mutate( final double probability ) {
		if ( Math.random() >= probability )
			return 0;

		int count = 0;
		do {
			rotate();
			count++;
		} while ( Math.random() < probability );

		return count;
	}

	@SuppressWarnings ( "unused" )
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
		pieces[y][x].rotate( (int) GaUtil.random( 1, 3 ) );

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
						pieces[y][x] = change.copy();
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

	@Override
	public Board copy() {
		final Piece[][] pieces = new Piece[boardSize][boardSize];
		int i = 0;
		for ( final Piece[] row : this.pieces ) {
			int j = 0;
			for ( final Piece piece : row ) {
				pieces[i][j] = piece.copy();
				j++;
			}
			i++;
		}

		return new Board( pieces, boardSize, fitness );
	}

}
