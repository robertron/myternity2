package de.robertron.myternity2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.robertron.myternity2.ga.GaUtil;
import de.robertron.myternity2.ga.Individuum;
import de.robertron.myternity2.ga.Population;

public class PopulationImpl
		implements Population<Piece, Individuum<Piece>> {

	private final int turnamentSize;
	private final File file;
	private final int boardSize;
	private List<Individuum<Piece>> individuums;
	private int run;

	public PopulationImpl( final int boardSize, final int turnamentSize, final File file ) {
		this.turnamentSize = turnamentSize;
		this.file = file;
		this.boardSize = boardSize;
		this.individuums = new ArrayList<Individuum<Piece>>();
		run = 0;
	}

	@Override
	public void select( final int elite ) {
		final List<Individuum<Piece>> newIndividuums = new ArrayList<Individuum<Piece>>();
		int size = individuums.size();
		if ( elite > 0 ) {
			final List<Individuum<Piece>> best = GaUtil.best( this.individuums, elite );
			newIndividuums.addAll( best );
			size--;
		}
		while ( size >= 0 ) {
			newIndividuums.add( GaUtil.tournament( this.individuums, turnamentSize ) );
			size--;
		}

		run++;
		individuums = newIndividuums;
	}

	@Override
	public Population<Piece, Individuum<Piece>> initial( final int populationSize ) {
		try {
			final List<Piece> pieces = Converter.loadPieces( file, boardSize );
			for ( int i = 0; i < populationSize; i++ ) {
				Collections.shuffle( pieces );
				individuums.add( Board.from( pieces, boardSize ) );
			}
			this.run = 0;
			return this;

		} catch ( final FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( final IOException e ) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void cross( final double fraction, final double probability ) {
		for ( int i = 0; i < ( (int) ( individuums.size() * fraction ) & ~1 ); i += 2 ) {
			cross( individuums.get( i ), individuums.get( i + 1 ), probability );
		}
	}

	private void cross( final Individuum<Piece> individuum1, final Individuum<Piece> individuum2,
			final double probability ) {
		final List<Piece> change = new ArrayList<Piece>();
		final List<Piece> genes = individuum1.getGenes();
		for ( int i = 0; i < genes.size(); i++ ) {
			if ( Math.random() <= probability ) {
				change.add( genes.get( i ) );
			}
		}

		final List<Piece> cross = individuum1.cross( change );
		individuum2.cross( cross );
	}

	@Override
	public void mutate( final double mutationProbability ) {
		for ( final Individuum<Piece> individuum : this.individuums ) {
			individuum.mutate( mutationProbability );
		}
	}

	@Override
	public boolean finished( final int runs, final int winningFitness ) {
		return run > runs || GaUtil.maximalFitness( individuums ) >= winningFitness;
	}

}
