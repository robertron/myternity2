package de.robertron.myternity2.model;

import org.junit.Assert;
import org.junit.Test;

public class PieceTest {

	@Test
	public void testGet() {
		Piece p = Piece.from( 23, 42, 12, 1, 2 );
		Assert.assertEquals( 23, p.get( Direction.NORTH ) );
		Assert.assertEquals( 42, p.get( Direction.EAST ) );
		Assert.assertEquals( 12, p.get( Direction.SOUTH ) );
		Assert.assertEquals( 1, p.get( Direction.WEST ) );
	}

	@Test
	public void testSet() {
		Piece p = Piece.from( 23, 42, 12, 1, 2 );
		for ( Direction d : Direction.values() ) {
			p.set( d, d.ordinal() + 100 );
		}
		for ( Direction d : Direction.values() ) {
			Assert.assertEquals( d.ordinal() + 100, p.get( d ) );
		}
	}

	@Test
	public void testCopy() {
		Piece p = Piece.from( 23, 42, 12, 1, 2 );
		Piece p2 = p.copy();
		Assert.assertNotSame( p, p2 );
		for ( Direction d : Direction.values() ) {
			Assert.assertEquals( p.get( d ), p2.get( d ) );
		}
	}

	@Test
	public void testRotate() {
		Piece p = Piece.from( 23, 42, 12, 1, 2 );
		Piece expected = Piece.from( 42, 12, 1, 23, 2 );
		Piece rotated = p.copy();
		rotated.rotate();
		for ( Direction d : Direction.values() ) {
			Assert.assertEquals( expected.get( d ), rotated.get( d ) );
		}
	}
}
