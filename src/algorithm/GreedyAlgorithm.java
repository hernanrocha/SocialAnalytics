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
		log.info("[Greedy Algorithm]");

		Set<Vertex> solution = new HashSet<Vertex>();
		Set<Vertex> vertices = sn.getVertices();
		
		for (int i = 0; i < n; i++){
			Double bestMarginal = 0.0;
			Vertex bestVertex = null;

			for (Vertex vertex : vertices) {

				log.info("Verice " + vertex);
				
				// Algoritmo de calculo de spread para calcular marginal
				Double newMarginal = getMarginal(sn, spread, model, solution, vertex);
				
				if (newMarginal > bestMarginal) {
					bestMarginal = newMarginal;
					bestVertex = vertex;
				}
			}
			
			solution.add(bestVertex);
			vertices.remove(bestVertex);
			
			log.info("Seleccionar " + bestVertex + " (marginal " + bestMarginal + ")");
		}
		
		return solution;
	}

}
