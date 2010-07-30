package de.robertron.myternity2;

import java.io.File;

import de.robertron.myternity2.config.Configuration;
import de.robertron.myternity2.config.Key;
import de.robertron.myternity2.ga.GeneticAlgorithm;
import de.robertron.myternity2.ga.Population;
import de.robertron.myternity2.model.Board;
import de.robertron.myternity2.model.Piece;
import de.robertron.myternity2.model.PopulationImpl;

public class Myternity2 {

	public static void main( final String[] args ) {

		final File file = new File( Configuration.getString( Key.MODEL_FILE ) );

		final GeneticAlgorithm alg = new GeneticAlgorithm();
		alg.setWinningFitness( Configuration.getInt( Key.GA_WINNINGFITNESS ) );
		alg.setCrossoverFraction( Configuration.getDouble( Key.GA_CROSSOVERFRACTION ) );
		alg.setCrossoverProbability( Configuration.getDouble( Key.GA_CROSSOVERPROBABILITY ) );
		alg.setElite( Configuration.getInt( Key.GA_ELITE ) );
		alg.setMutationPropability( Configuration.getDouble( Key.GA_MUTATIONPROBABILITY ) );
		alg.setRuns( Configuration.getInt( Key.GA_RUNS ) );

		final Population<Piece, Board> population = new PopulationImpl(
				Configuration.getInt( Key.MODEL_BOARDSIZE ),
				Configuration.getInt( Key.GA_TURNAMENTSIZE ),
				Configuration.getInt( Key.GA_POPULATION ), file );
		alg.evolution( population );
	}
}
