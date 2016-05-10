package algorithm;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class CelfAlgorithm extends MaximizationAlgorithm {
	
	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		System.out.println("[CELF Algorithm]");
		
		if (n == 0){
			return new HashSet<Vertex>();
		}
		
		Set<Vertex> vertices = sn.getVertices();
		SortedSet<CelfVertex> marginals = new TreeSet<CelfVertex>(Comparator.comparing(CelfVertex::getMarginal));
		Set<Vertex> solution = new HashSet<Vertex>();
		
		// Calcular marginales iniciales
		for (Vertex v : vertices) {
			Set<Vertex> seedSet = new HashSet<Vertex>();
			seedSet.add(v);
			
			// Utilizar algoritmo de calculo de spread para calcular marginal
			Double marginal = spread.calculateSpread(sn, seedSet, model);
			marginals.add(new CelfVertex(v, marginal));			
		}
		System.out.println("Elijo " + marginals.last().getVertex());
		solution.add(marginals.last().getVertex());
		marginals.remove(marginals.last());
		int i = 1;

		while (i < n){
			// Obtener el vertice con mayor ganancia marginal
			CelfVertex last = marginals.last();
			marginals.remove(last);
			
			// Algoritmo de calculo de spread para calcular marginal
			Double newMarginal = getMarginal(sn, spread, model, solution, last.getVertex());
			
			if (newMarginal >= marginals.last().getMarginal()) {
				// Si sigue siendo la mejor, agregar al conjunto solution
				System.out.println("Elijo " + last.getVertex());
				solution.add(last.getVertex());
				i++;
			} else {
				// Devolverlo a la lista con la ganancia marginal actualizada
				System.out.println(" Actualizo " + last.getVertex());
				marginals.add(new CelfVertex(last.getVertex(), newMarginal));
			}			
		}
		
		return solution;
	}

}
