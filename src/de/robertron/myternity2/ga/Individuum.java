package de.robertron.myternity2.ga;

import java.util.List;

public interface Individuum<T extends Gene> {

	List<T> getGenes();

	int getFitness();

	void mutate( final double mutationProbability );

	List<T> cross( final List<T> genes );
}
