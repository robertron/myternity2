package de.robertron.myternity2.ga;

public interface Population<K extends Gene, T extends Individuum<K>> {

	Population<K, T> initial( final int populationSize );

	void mutate( double probability );

	boolean finished( int runs, int winningFitness );

	void select( int elite );

	void cross( double fraction, double probability );

}