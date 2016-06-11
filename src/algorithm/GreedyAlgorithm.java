package algorithm;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class GreedyAlgorithm extends MaximizationAlgorithm {

	static Logger log = Logger.getLogger(GreedyAlgorithm.class.getName());
	
	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		log.warn("[Greedy Algorithm]");

		Set<Vertex> solution = new HashSet<Vertex>();
		Set<Vertex> vertices = sn.getVertices();
		
		for (int i = 0; i < n; i++){
			Double bestMarginal = 0.0;
			Vertex bestVertex = null;

			int j = 1;
			for (Vertex vertex : vertices) {
				int a = j * 100;
				int b = vertices.size() * n;
				updateProgress(a / b);
				
				// Algoritmo de calculo de spread para calcular marginal
				Double newMarginal = getMarginal(sn, spread, model, solution, vertex);
				
				if (newMarginal > bestMarginal) {
					bestMarginal = newMarginal;
					bestVertex = vertex;
				}
				
				j++;
			}
			
			solution.add(bestVertex);
			vertices.remove(bestVertex);
			
			log.warn("Seleccionar " + bestVertex + " (marginal " + bestMarginal + ")");
		}
		
		return solution;
	}

}
