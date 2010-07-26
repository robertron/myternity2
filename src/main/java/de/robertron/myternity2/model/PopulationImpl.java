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
		implements Population<Piece, Board> {

	private final int turnamentSize;
	private final File file;
	private final int boardSize;
	private List<Board> individuums;
	private int run;
	private final int popsize;

	public PopulationImpl( final int boardSize, final int turnamentSize, final int popsize,
			final File file ) {
		this.turnamentSize = turnamentSize;
		this.popsize = popsize;
		this.file = file;
		this.boardSize = boardSize;
		this.individuums = new ArrayList<Board>();
		run = 0;
	}

	@Override
	public void select( final int elite ) {
		final List<Board> newIndividuums = new ArrayList<Board>();
		int size = individuums.size();
		if ( elite > 0 ) {
			final List<Board> best = GaUtil.best( this.individuums, elite );

			System.out.println( "ELITE BOARDS:" );
			int i = 0;
			for ( final Board board : best ) {
				System.out.println( i + ". " + board.fitness() );
				i++;
			}

			newIndividuums.addAll( GaUtil.copy( best ) );
			size -= best.size();
		}
		while ( size > 0 ) {
			final Board tournament = GaUtil.tournament( this.individuums, turnamentSize );
			newIndividuums.add( tournament.copy() );
			size--;
		}

		run++;
		individuums = newIndividuums;
	}

	@Override
	public Population<Piece, Board> initial() {
		try {
			final List<Piece> pieces = Converter.loadPieces( file, boardSize );
			for ( int i = 0; i < popsize; i++ ) {
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
		int count = 0;
		for ( int i = 0; i < ( (int) ( individuums.size() * fraction ) & ~1 ); i += 2 ) {
			cross( individuums.get( i ), individuums.get( i + 1 ), probability );
			count++;
		}
		System.out.println( "CROSSOVER OPERATIONS: " + count );
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
		int count = 0;
		for ( final Individuum<Piece> individuum : this.individuums ) {
			count += individuum.mutate( mutationProbability );
		}
		System.out.println( "MUTATIONS: " + count );
	}

	final boolean sane() {
		for ( final Board board : individuums ) {
			if ( !board.sane() ) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean finished( final int runs, final int winningFitness ) {
		final int maximalFitness = GaUtil.maximalFitness( individuums );
		System.out.println( "################################ RUN " + run
				+ " ##########################################" );
		System.out.println( "CHECK POPULATION: BEST: " + maximalFitness );

		if ( !sane() || individuums.size() != popsize ) {
			System.err.println( "ERROR: Population insane" );
			return true;
		}

		if ( run > runs || maximalFitness >= winningFitness ) {
			log();
			return true;
		}

		return false;
	}

	public void log() {
		int i = 0;
		System.out.println( "\n##################### FINAL POPULATION ###################################" );
		for ( final Board board : individuums ) {
			System.out.println( "--------------------------------" );
			System.out.println( "--- BOARD: " + i + " FITNESS: " + board.fitness() );
			System.out.println( "--------------------------------" );
			System.out.println( board );
			i++;
		}
	}

	@Override
	public void calculate() {
		for ( final Individuum<Piece> individuum : this.individuums ) {
			individuum.calculate();
		}
	}

}
