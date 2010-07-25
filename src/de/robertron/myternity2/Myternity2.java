package de.robertron.myternity2;

import java.io.File;

import de.robertron.myternity2.ga.GeneticAlgorithm;
import de.robertron.myternity2.ga.Individuum;
import de.robertron.myternity2.ga.Population;
import de.robertron.myternity2.model.Piece;
import de.robertron.myternity2.model.PopulationImpl;

public class Myternity2 {

	public static void main( final String[] args ) {
		final File file = new File( args[0] );
		final GeneticAlgorithm alg = new GeneticAlgorithm( 256 );
		final Population<Piece, Individuum<Piece>> population = new PopulationImpl( 16, 5, file );
		alg.evolution( population );
	}
}
