package algorithm.spread;

import java.util.Set;

import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public abstract class SpreadCalculator {
	
	public Double calculateSpread(SocialNetwork sn, Set<Vertex> seedSet, PropagationModel model) {
		return calculateSpread(sn, seedSet, model, false);
	}

	public abstract Double calculateSpread(SocialNetwork sn, Set<Vertex> seedSet, PropagationModel model, boolean drawGraph);
	
}
