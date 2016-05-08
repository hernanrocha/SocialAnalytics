package algorithm.spread;

import java.util.Set;

import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class MontecarloCalculator extends SpreadCalculator {
	private static final int DEFAULT_RUNS = 1;
	
	int runs = DEFAULT_RUNS;

	@Override
	public Double calculateSpread(SocialNetwork sn, Set<Vertex> seedSet, PropagationModel model) {
		System.out.println("[Montecarlo Calculator]");
		
		int total = 0;
		for (int i = 0; i < runs; i++){
			total += model.propagate(sn, seedSet);
		}
		return ((double) total) / runs;
	}

}
