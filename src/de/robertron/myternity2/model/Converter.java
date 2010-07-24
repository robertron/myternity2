package de.robertron.myternity2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

public class Converter {

	public static Board load( final File file )
			throws FileNotFoundException, IOException {
		final StringBuffer buffer = new StringBuffer();

		final LineNumberReader lnr = new LineNumberReader( new FileReader( file ) );

		String line = lnr.readLine();

		while ( line != null ) {
			buffer.append( line );
			buffer.append( '\n' );
			line = lnr.readLine();
		}
		return null;
	}

	private static Piece[] convert( final String line, final int size ) {
		final StringTokenizer stok = new StringTokenizer( line );

		int i = 0;
		while ( stok.hasMoreTokens() ) {
			Piece piece;
			final String token = stok.nextToken();
			if ( i % 4 == 0 ) {
				piece = new Piece();
			}
			if ( i % 4 == 1 ) {

			}
			if ( i % 4 == 2 ) {

			}
			if ( i % 4 == 3 ) {

			}

			System.out.println( token );
			i++;
		}
		return null;

	}
}
