package de.robertron.myternity2.config;

public enum Key {

	MODEL_FILE( "model.file" ),
	MODEL_BOARDSIZE( "model.boardsize" ),
	GA_WINNINGFITNESS( "ga.winningFitness" ),
	GA_POPULATION( "ga.population" ),
	GA_TURNAMENTSIZE( "ga.turnamentSize" ),
	GA_ELITE( "ga.elite" ),
	GA_RUNS( "ga.runs" ),
	GA_CROSSOVERFRACTION( "ga.crossoverFraction" ),
	GA_MUTATIONPROBABILITY( "ga.mutationProbability" ),
	GA_CROSSOVERPROBABILITY( "ga.crossoverProbability" ),
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
