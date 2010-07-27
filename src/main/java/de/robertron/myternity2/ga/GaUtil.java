package de.robertron.myternity2.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GaUtil {

	public static class FITNESS_COMPARATOR
			implements Comparator<Individuum<?>> {

		@Override
		public int compare( final Individuum<?> individuum1, final Individuum<?> individuum2 ) {
			return Integer.valueOf( individuum1.fitness() ).compareTo(
					Integer.valueOf( individuum2.fitness() ) );
		}
	}

	public static double random( final double min, final double max ) {
		return min + (int) ( Math.random() * ( ( max - min ) + 1 ) );

	}

	public static double random( final double min, final double max, final int scale ) {
		return random( min * scale, max * scale ) / scale;
	}

	public static <K extends Gene, T extends Individuum<K>> T tournament(
			final List<T> individuums, final int turnamentSize ) {
		T best = individuums.get( (int) GaUtil.random( 0, individuums.size() - 1 ) );
		int run = turnamentSize - 1;
		while ( run > 0 ) {
			final T ind = individuums.get( (int) GaUtil.random( 0, individuums.size() - 1 ) );
			if ( ind.fitness() > best.fitness() ) {
				best = ind;
			}
			run--;
		}
		return best;
	}

	public static <K extends Gene, T extends Individuum<K>> int bestFitness(
			final List<T> individuums ) {
		final T individuum = Collections.max( individuums, new FITNESS_COMPARATOR() );
		return individuum.fitness();
	}

	public static <K extends Gene, T extends Individuum<K>> List<T> best(
			final List<T> individuums, final int n ) {
		Collections.sort( individuums, new FITNESS_COMPARATOR() );
		Collections.reverse( individuums );
		return individuums.subList( 0, n );
	}

	public static <T extends Copyable<T>> List<T> copy( final List<T> copyList ) {
		final List<T> result = new ArrayList<T>();

		for ( final T element : copyList ) {
			result.add( element.copy() );
		}

		return result;
	}
}
