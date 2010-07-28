package de.robertron.myternity2.config;

public enum Key {

	MODEL_FILE( "model.file" ),
	MODEL_BOARDSIZE( "model.boardsize" ),
	GA_WINNINGFITNESS( "ga.winning.fitness" ),
	GA_POPULATION( "ga.population" ),
	GA_TURNAMENTSIZE( "ga.size.turnament" ),
	GA_ELITE( "ga.elite" ),
	GA_RUNS( "ga.runs" ),
	GA_CROSSOVERFRACTION( "ga.fraction.crossover" ),
	GA_MUTATIONPROBABILITY( "ga.probability.mutation" ),
	GA_CROSSOVERPROBABILITY( "ga.probability.crossover" ),
	GA_SAVEELITEIN( "ga.save.elite.in" ),
	FITNESS_REWARD( "fitness.reward" ),
	FITNESS_REWARDEDGE( "fitness.reward.edge" );

	private final String key;

	private Key( final String key ) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
