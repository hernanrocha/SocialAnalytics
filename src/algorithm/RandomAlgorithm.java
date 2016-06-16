package algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class RandomAlgorithm extends MaximizationAlgorithm {

	static Logger log = Logger.getLogger(RandomAlgorithm.class.getName());
	
	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		log.info("[Random Algorithm]");

		Set<Vertex> vertices = sn.getVertices();
		Vertex[] a = new Vertex[vertices.size()];
		vertices.toArray(a);
		Vector<Vertex> v = new Vector<Vertex>(Arrays.asList(a));
		
		log.info("Elegir " + n + " entre " + a.length);
		Set<Vertex> solution = new HashSet<Vertex>();

		updateProgress(0);
		
		for (int i = 0; i < n; i++){
			Integer size = v.size();
			
			int selected = (int) Math.floor(Math.random() * size);
			log.info("Seleccionar " + v.get(selected));
			updateProgress((i+1) * 100 / n);
			
			solution.add(v.get(selected));
			v.remove(selected);
		}
		
		return solution;
	}

	@Override
	public MaximizationAlgorithm instance() {
		return new RandomAlgorithm();
	}

}
