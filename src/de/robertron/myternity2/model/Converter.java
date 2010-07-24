package de.robertron.myternity2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


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

}
