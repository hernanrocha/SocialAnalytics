package algorithm;

import java.util.Set;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public abstract class MaximizationAlgorithm {

	// Dada una red social y un entero n, encontrar las n semillas
	// que maximicen la influencia sobre la red
	public abstract Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n);
	
}
