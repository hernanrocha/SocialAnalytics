package algorithm.spread;

import java.util.Set;

import org.apache.log4j.Logger;

import propagation.IndependentCascadeModel;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class MontecarloCalculator extends SpreadCalculator {
	
	private static final int DEFAULT_RUNS = 1;
	
	static Logger log = Logger.getLogger(IndependentCascadeModel.class.getName());
	
	private Integer runs;
	
	public MontecarloCalculator(Integer runs) {
		this.runs = runs;
	}
	
	public MontecarloCalculator() {
		this(DEFAULT_RUNS);
	}

	@Override
	public Double calculateSpread(SocialNetwork sn, Set<Vertex> seedSet, PropagationModel model) {
		log.trace("[Montecarlo Calculator]");
		
		int total = 0;
		for (int i = 0; i < runs; i++){
			total += model.propagate(sn, seedSet);
		}
		
		return ((double) total) / runs;
	}

}
