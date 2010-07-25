package de.robertron.myternity2;

import java.io.File;

import de.robertron.myternity2.ga.GeneticAlgorithm;
import de.robertron.myternity2.ga.Population;
import de.robertron.myternity2.model.Board;
import de.robertron.myternity2.model.Piece;
import de.robertron.myternity2.model.PopulationImpl;

public class Myternity2 {

	public static void main( final String[] args ) {
		final File file = new File( "etc/3x3.txt" );
		final GeneticAlgorithm alg = new GeneticAlgorithm( 9 );
		final Population<Piece, Board> population = new PopulationImpl( 3, 5, 10, file );
		alg.evolution( population );
	}
}
