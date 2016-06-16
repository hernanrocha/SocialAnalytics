package algorithm.spread;

import java.util.Set;

import org.apache.log4j.Logger;

import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class MontecarloCalculator extends SpreadCalculator {
	
	private static final int DEFAULT_RUNS = 5;
	
	static Logger log = Logger.getLogger(MontecarloCalculator.class.getName());
	
	private Integer runs = DEFAULT_RUNS;
	
	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	@Override
	public Double calculateSpread(SocialNetwork sn, Set<Vertex> seedSet, PropagationModel model, boolean drawGraph) {
		log.trace("[Montecarlo Calculator]");
		
		int total = 0;
		for (int i = 0; i < runs; i++){
			log.trace("Run " + (i+1) + "/" + runs);
			total += model.propagate(sn, seedSet, drawGraph);
		}
		
		double avg = ((double) total) / runs;
		log.trace("Average: " + avg);
		
		return avg;
	}

}
