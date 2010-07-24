package de.robertron.myternity2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.robertron.myternity2.model.Board;
import de.robertron.myternity2.model.Converter;

public class Myternity2 {

	public static void main( final String[] args ) {
		try {
			final File file = new File( args[0] );
			final Board buffer = Converter.load( file );

			System.out.println( buffer.toString() );

		} catch ( final FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( final IOException e ) {
			e.printStackTrace();
		}

	}

}
