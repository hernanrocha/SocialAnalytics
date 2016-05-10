package algorithm;

import java.util.HashSet;
import java.util.Set;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class GreedyAlgorithm extends MaximizationAlgorithm {

	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		System.out.println("[Greedy Algorithm]");

		Set<Vertex> solution = new HashSet<Vertex>();
		Set<Vertex> vertices = sn.getVertices();
		
		for (int i = 0; i < n; i++){
			Double bestMarginal = 0.0;
			Vertex bestVertex = null;

			for (Vertex vertex : vertices) {
				// Algoritmo de calculo de spread para calcular marginal
				Double newMarginal = getMarginal(sn, spread, model, solution, vertex);
				
				if (newMarginal > bestMarginal) {
					bestMarginal = newMarginal;
					bestVertex = vertex;
				}
			}
			
			solution.add(bestVertex);
			vertices.remove(bestVertex);
		}
		
		return null;
	}

}
