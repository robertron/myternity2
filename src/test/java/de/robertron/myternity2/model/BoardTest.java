package de.robertron.myternity2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class BoardTest {

	@Test
	public void loadBoardTest() {
		try {
			final Board board = Converter.loadBoard( new File( "etc/3x3.txt" ), 3 );
			board.calculate();
			Assert.assertEquals( 9, board.fitness() );
		} catch ( final FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( final IOException e ) {
			e.printStackTrace();
		}
	}

}
