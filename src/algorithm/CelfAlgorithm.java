package algorithm;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class CelfAlgorithm extends MaximizationAlgorithm {
	
	static Logger log = Logger.getLogger(CelfAlgorithm.class.getName());
	
	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		log.info("[CELF Algorithm]");
		
		if (n == 0){
			return new HashSet<Vertex>();
		}
		
		Set<Vertex> vertices = sn.getVertices();
		SortedSet<CelfVertex> marginals = new TreeSet<CelfVertex>();
		Set<Vertex> solution = new HashSet<Vertex>();
		
		// Calcular marginales iniciales
		int j = 1;
		for (Vertex v : vertices) {
			Set<Vertex> seedSet = new HashSet<Vertex>();
			seedSet.add(v);
			
			// Utilizar algoritmo de calculo de spread para calcular marginal
			//Double marginal = spread.calculateSpread(sn, seedSet, model);
			Double marginal = getMarginal(sn, spread, model, solution, v);
			marginals.add(new CelfVertex(v, marginal));
			
			int a = j * 100;
			int b = vertices.size() * n;
			updateProgress(a / b);
			
			j++;
		}
		
		log.warn("Elijo " + marginals.last().getVertex() + " (marginal " + marginals.last().getMarginal() + ")");
		solution.add(marginals.last().getVertex());
		marginals.remove(marginals.last());
		int i = 1;

		while (i < n){
			log.warn(i + " de " + n);
			
			// Obtener el vertice con mayor ganancia marginal
			CelfVertex last = marginals.last();
			marginals.remove(last);
			
			// Algoritmo de calculo de spread para calcular marginal
			log.warn("Solution: " + solution);
			log.warn("Vertex: " + last.getVertex());
			Double newMarginal = getMarginal(sn, spread, model, solution, last.getVertex());
			log.warn("Marginal: " + newMarginal);
			
			if (newMarginal >= marginals.last().getMarginal()) {
				// Si sigue siendo la mejor, agregar al conjunto solution
				log.warn("Elijo " + last.getVertex() + " (marginal " + last.getMarginal() + ")");
				solution.add(last.getVertex());
				i++;
			} else {
				// Devolverlo a la lista con la ganancia marginal actualizada
				log.warn(" Actualizo " + last.getVertex());
				marginals.add(new CelfVertex(last.getVertex(), newMarginal));
			}
			
			//updateProgress(i * 100 / n);
		}
		
		log.warn("Finalizado");
		
		return solution;
	}

}
