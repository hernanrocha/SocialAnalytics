package algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class RandomAlgorithm extends MaximizationAlgorithm {

	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		System.out.println("[Random Algorithm]");

		Set<Vertex> vertices = sn.getVertices();
		Vertex[] a = new Vertex[vertices.size()];
		vertices.toArray(a);
		Vector<Vertex> v = new Vector<Vertex>(Arrays.asList(a));
		
		System.out.println("Elegir " + n + " entre " + a.length);
		Set<Vertex> solution = new HashSet<Vertex>();
		
		for (int i = 0; i < n; i++){
			Integer size = v.size();
			System.out.println("Quedan " + size);
			
			int selected = (int) Math.floor(Math.random() * size);
			System.out.println("Elijo " + selected);
			
			solution.add(v.get(selected));
			v.remove(selected);
		}
		
		return solution;
	}

}
