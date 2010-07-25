package de.robertron.myternity2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Converter {

	public static Board loadBoard( final File file, final int size )
			throws FileNotFoundException, IOException {
		return Board.from( load( file, size ), size );
	}

	public static List<Piece> loadPieces( final File file, final int size )
			throws FileNotFoundException, IOException {

		final Piece[][] pieces = load( file, size );
		final List<Piece> result = convert( pieces );

		return result;
	}

	public static List<Piece> convert( final Piece[][] pieces ) {
		final List<Piece> result = new ArrayList<Piece>();
		for ( final Piece[] row : pieces ) {
			for ( final Piece piece : row ) {
				result.add( piece );
			}
		}
		return result;
	}

	private static Piece[][] load( final File file, final int size )
			throws FileNotFoundException, IOException {
		final LineNumberReader lnr = new LineNumberReader( new FileReader( file ) );
		final Piece[][] rows = new Piece[size][];

		String line = lnr.readLine();

		int i = 0;
		while ( line != null ) {
			rows[i] = parse( line, size, i );
			line = lnr.readLine();
			i++;
		}
		return rows;
	}

	private static Piece[] parse( final String line, final int size, final int linenumber ) {
		final StringTokenizer stok = new StringTokenizer( line );
		final Piece[] pieces = new Piece[size];

		int i = 0;
		while ( stok.hasMoreTokens() ) {
			final Integer north = Integer.valueOf( stok.nextToken() );
			final Integer east = Integer.valueOf( stok.nextToken() );
			final Integer south = Integer.valueOf( stok.nextToken() );
			final Integer west = Integer.valueOf( stok.nextToken() );

			pieces[i] = Piece.from( north, east, south, west, ( linenumber * size ) + i );
			i++;
		}
		return pieces;

	}
}
