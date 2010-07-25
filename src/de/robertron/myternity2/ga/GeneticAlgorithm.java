package de.robertron.myternity2.ga;

public class GeneticAlgorithm {

	private int elite;
	private int popsize;
	private int runs;
	private final int winningFitness;
	private double crossoverFraction;
	private final double crossoverProbability;
	private double mutationProbability;

	public GeneticAlgorithm( final int winningFitness ) {
		this.winningFitness = winningFitness;
		this.elite = 5;
		this.popsize = 30;
		this.runs = 20;
		this.crossoverFraction = 0.3;
		this.mutationProbability = 0.1;
		this.crossoverProbability = 0.5;
	}

	public void evolution( final Population<?, ?> prototype ) {
		final Population<?, ?> pop = prototype.initial( popsize );
		while ( !pop.finished( runs, winningFitness ) ) {
			pop.select( elite );
			pop.cross( crossoverFraction, crossoverProbability );
			pop.mutate( mutationProbability );
		}
	}

	public void setElite( final int elite ) {
		this.elite = elite;
	}

	public void setPopsize( final int popsize ) {
		this.popsize = popsize;
	}

	public void setRuns( final int runs ) {
		this.runs = runs;
	}

	public void setCrossoverFraction( final double crossoverFraction ) {
		this.crossoverFraction = crossoverFraction;
	}

	public void setMutationPropability( final double mutationPropability ) {
		this.mutationProbability = mutationPropability;
	}

}
