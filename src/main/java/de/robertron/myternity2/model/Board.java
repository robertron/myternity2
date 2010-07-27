package de.robertron.myternity2.model;

import java.util.ArrayDeque;
import java.util.Deque;
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
		arrange();
	}

	public static Board from( final List<Piece> pieces, final int boardSize ) {

		final Deque<Piece> corners = new ArrayDeque<Piece>();
		final Deque<Piece> borders = new ArrayDeque<Piece>();
		final Deque<Piece> normal = new ArrayDeque<Piece>();

		for ( final Piece piece : pieces ) {
			switch ( piece.getType() ) {
				case NORMAL:
					normal.add( piece );
					break;
				case BORDER:
					borders.add( piece );
					break;
				case CORNER:
					corners.add( piece );
					break;
			}
		}

		return new Board( create( boardSize, corners, borders, normal ), boardSize, 0 ).copy();
	}

	private static Piece[][] create( final int boardSize, final Deque<Piece> corners,
			final Deque<Piece> borders, final Deque<Piece> normal ) {
		final Piece[][] result = new Piece[boardSize][];
		for ( int i = 0; i < boardSize; i++ ) {
			final Piece[] row = new Piece[boardSize];
			for ( int j = 0; j < boardSize; j++ ) {
				if ( isCorner( i, j, boardSize ) ) {
					row[j] = corners.pop();
				} else if ( isBorder( i, j, boardSize ) ) {
					row[j] = borders.pop();
				} else {
					row[j] = normal.pop();
				}
			}
			result[i] = row;
		}
		return result;
	}

	private static boolean isBorder( final int x, final int y, final int boardSize ) {
		return x == 0 || x == boardSize - 1 || y == 0 || y == boardSize - 1;
	}

	private static boolean isCorner( final int x, final int y, final int boardsize ) {
		return ( x == 0 && y == 0 ) || ( x == 0 && y == boardsize - 1 )
				|| ( x == boardsize - 1 && y == 0 ) || ( x == boardsize - 1 && y == boardsize - 1 );
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

	public int cross( final double probability ) {
		if ( Math.random() >= probability )
			return 0;

		int count = 0;
		do {
			cross();
			count++;
		} while ( Math.random() < probability );

		return count;
	}

	private void cross() {
		crossCorner();
		crossBorder();
		crossNormal();
	}

	private void crossNormal() {
		change( randomNormal(), randomNormal() );
	}

	private void crossBorder() {
		change( randomBorder(), randomBorder() );
	}

	private void crossCorner() {
		change( randomCorner(), randomCorner() );
	}

	private void change( final Coordinate coord1, final Coordinate coord2 ) {
		final Piece piece = pieces[coord1.getY()][coord1.getX()];
		final Piece piece2 = pieces[coord2.getY()][coord2.getX()];

		if ( piece.getType() != piece2.getType() ) {
			throw new IllegalArgumentException( "Versuche " + piece.getType() + " mit "
					+ piece2.getType() + " zu tauschen." );
		}
		pieces[coord1.getY()][coord1.getX()] = pieces[coord2.getY()][coord2.getX()];
		pieces[coord2.getY()][coord2.getX()] = piece;
	}

	private void arrange() {
		int y = 0;
		for ( final Piece[] row : pieces ) {
			int x = 0;
			for ( final Piece piece : row ) {
				if ( piece.getType() == PieceType.CORNER ) {
					rotateCorner( x, y, piece );
				} else if ( piece.getType() == PieceType.BORDER ) {
					rotateBorder( x, y, piece );
				}
				x++;
			}
			y++;
		}
	}

	private void rotateCorner( final int x, final int y, final Piece piece ) {
		if ( x == 0 && y == 0 ) {
			rotateBorder( piece, Direction.NORTH, Direction.WEST );
		}
		if ( x == 0 && y == boardSize - 1 ) {
			rotateBorder( piece, Direction.SOUTH, Direction.WEST );
		}
		if ( y == 0 && x == boardSize - 1 ) {
			rotateBorder( piece, Direction.NORTH, Direction.EAST );
		}
		if ( y == boardSize - 1 && x == boardSize - 1 ) {
			rotateBorder( piece, Direction.SOUTH, Direction.EAST );
		}
	}

	private void rotateBorder( final int x, final int y, final Piece piece ) {
		if ( x == 0 ) {
			rotateBorder( piece, Direction.WEST );
		}
		if ( x == boardSize - 1 ) {
			rotateBorder( piece, Direction.EAST );
		}
		if ( y == 0 ) {
			rotateBorder( piece, Direction.NORTH );
		}
		if ( y == boardSize - 1 ) {
			rotateBorder( piece, Direction.SOUTH );
		}
	}

	private void rotateBorder( final Piece piece, final Direction... directions ) {
		if ( borderSum( piece, directions ) == 0 ) {
			return;
		}

		for ( int i = 0; i < 3; i++ ) {
			piece.rotate( 1 );
			if ( borderSum( piece, directions ) == 0 ) {
				return;
			}
		}
		throw new IllegalStateException( "NO BORDER OR CORNER!!" );
	}

	private int borderSum( final Piece piece, final Direction... directions ) {
		int count = 0;
		for ( final Direction direction : directions ) {
			count += piece.get( direction );
		}
		return count;
	}

	private void rotate() {
		final Coordinate c = randomNormal();
		final Piece piece = pieces[c.getY()][c.getX()];
		if ( piece.getType() == PieceType.NORMAL ) {
			piece.rotate( (int) GaUtil.random( 1, 3 ) );
		}
	}

	private Coordinate randomNormal() {
		final int x = (int) GaUtil.random( 1, boardSize - 2 );
		final int y = (int) GaUtil.random( 1, boardSize - 2 );
		return Coordinate.from( x, y );
	}

	private Coordinate randomCorner() {
		return directionCoordinate( 0, randomDirection() );
	}

	private Coordinate randomBorder() {
		final int number = (int) GaUtil.random( 1, boardSize - 2 );
		return directionCoordinate( number, randomDirection() );
	}

	private Coordinate directionCoordinate( final int number, final Direction d ) {
		switch ( d ) {
			case NORTH:
				return Coordinate.from( number, 0 );
			case SOUTH:
				return Coordinate.from( number, boardSize - 1 );
			case WEST:
				return Coordinate.from( 0, number );
			case EAST:
				return Coordinate.from( boardSize - 1, number );
		}
		return null;
	}

	private Direction randomDirection() {
		final int ordinal = (int) GaUtil.random( 0, 3 );
		for ( final Direction direction : Direction.values() ) {
			if ( direction.ordinal() == ordinal ) {
				return direction;
			}
		}
		throw new IllegalStateException();
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
