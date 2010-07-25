package de.robertron.myternity2.ga;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GaUtil {

	public static class FITNESS_COMPARATOR
			implements Comparator<Individuum<?>> {

		@Override
		public int compare( final Individuum<?> individuum1, final Individuum<?> individuum2 ) {
			return Integer.valueOf( individuum1.getFitness() ).compareTo(
					Integer.valueOf( individuum2.getFitness() ) );
		}
	}

	public static double random( final double min, final double max ) {
		return min + Math.floor( Math.random() * ( max - min + 1 ) );
	}

	public static double random( final double min, final double max, final int scale ) {
		return random( min * scale, max * scale ) / scale;
	}

	public static <T extends Gene> Individuum<T> tournament( final List<Individuum<T>> individuums,
			final int turnamentSize ) {
		Individuum<T> best = individuums.get( (int) GaUtil.random( 0, individuums.size() - 1 ) );
		int run = turnamentSize - 1;
		while ( run > 0 ) {
			final Individuum<T> ind = individuums.get( (int) GaUtil.random( 0,
					individuums.size() - 1 ) );
			if ( ind.getFitness() > best.getFitness() ) {
				best = ind;
			}
			run--;
		}
		return best;
	}

	public static <T extends Gene> int maximalFitness( final List<Individuum<T>> individuums ) {
		final Individuum<T> individuum = Collections.max( individuums, new FITNESS_COMPARATOR() );
		return individuum.getFitness();
	}

	public static <T extends Gene> List<Individuum<T>> best( final List<Individuum<T>> individuums,
			final int n ) {
		Collections.sort( individuums, new FITNESS_COMPARATOR() );
		return individuums.subList( 0, n );
	}

}
