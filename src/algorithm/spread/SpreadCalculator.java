package algorithm.spread;

import java.util.Set;

import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public abstract class SpreadCalculator {
	
	public abstract Double calculateSpread(SocialNetwork sn, Set<Vertex> seedSet, PropagationModel model);
	
}
