package de.robertron.myternity2.ga;

public class GeneticAlgorithm {

	private int elite;
	private int runs;
	private final int winningFitness;
	private double crossoverFraction;
	private double crossoverProbability;
	private double mutationProbability;

	public GeneticAlgorithm( final int winningFitness ) {
		this.winningFitness = winningFitness;
		this.elite = 2;
		this.runs = 2000;
		this.crossoverFraction = 0.2;
		this.mutationProbability = 0.3;
		this.crossoverProbability = 0.2;
	}

	public void evolution( final Population<?, ?> prototype ) {
		final Population<?, ?> pop = prototype.initial();
		pop.calculate();
		while ( !pop.finished( runs, winningFitness ) ) {
			pop.select( elite );
			pop.cross( crossoverFraction, crossoverProbability );
			pop.mutate( mutationProbability );
			pop.calculate();
		}
	}

	public void setElite( final int elite ) {
		this.elite = elite;
	}

	public void setRuns( final int runs ) {
		this.runs = runs;
	}

	public void setCrossoverFraction( final double crossoverFraction ) {
		this.crossoverFraction = crossoverFraction;
	}

	public void setCrossoverProbability( final double crossoverProbability ) {
		this.crossoverProbability = crossoverProbability;
	}

	public void setMutationPropability( final double mutationPropability ) {
		this.mutationProbability = mutationPropability;
	}

}
