package de.robertron.myternity2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.robertron.myternity2.ga.GaUtil;
import de.robertron.myternity2.model.Converter;
import de.robertron.myternity2.model.Piece;

public class Myternity2 {

	public static void main( final String[] args ) {
		try {
			final File file = new File( args[0] );
			final List<Piece> buffer = Converter.loadPieces( file, 16 );

			for ( int i = 0; i < 100; i++ ) {
				System.out.println( GaUtil.random( 0, 5 ) );
			}
		} catch ( final FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( final IOException e ) {
			e.printStackTrace();
		}

	}

}
