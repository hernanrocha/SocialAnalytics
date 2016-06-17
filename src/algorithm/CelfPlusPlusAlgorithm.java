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

public class CelfPlusPlusAlgorithm extends MaximizationAlgorithm {

	static Logger log = Logger.getLogger(CelfPlusPlusAlgorithm.class.getName());
	
	@Override
	public Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n) {
		log.info("[CELF++ Algorithm]");
		updateProgress(0);
		
		Set<Vertex> vertices = sn.getVertices();
		SortedSet<CelfPlusPlusVertex> Q = new TreeSet<CelfPlusPlusVertex>(); // Q = new Pila<CelfPlusPlusVertex>
		Set<Vertex> S = new HashSet<Vertex>(); // S = new HashSet<Vertex>
		CelfPlusPlusVertex last_seed = null;
		CelfPlusPlusVertex cur_best = null;

		for (Vertex v : vertices) {
			CelfPlusPlusVertex u = new CelfPlusPlusVertex(v);
			Set<Vertex> seed = new HashSet<Vertex>();
			seed.add(v);
			u.mg1 = spread.calculateSpread(sn, seed, model);
			u.prev_best = cur_best;
			if (cur_best != null) {
				seed.add(cur_best.vertex);
				u.mg2 = spread.calculateSpread(sn, seed, model);
			} else {
				u.mg2 = u.mg1;
			}
			u.flag = 0;

			Q.add(u);
			cur_best = (cur_best.mg1 < u.mg1) ? u : cur_best;
		}

		while (S.size() < n) {
			CelfPlusPlusVertex u = Q.last();
			if (u.flag == S.size()) {
				S.add(u.vertex);
				Q.remove(u); // Pop
				last_seed = u;
				continue;
			} else if (u.prev_best == last_seed) {
				u.mg1 = u.mg2;
			} else {
				u.mg1 = getMarginal(sn, spread, model, S, u.vertex); //delta u (S);
				u.prev_best = cur_best;
				S.add(cur_best.vertex);
				u.mg2 = getMarginal(sn, spread, model, S, u.vertex); // delta u ( S U {cur_best});
				S.remove(cur_best.vertex);
			}
			u.flag = S.size();
			cur_best = (cur_best.mg1 < u.mg1) ? u : cur_best; // Update cur_best
			Q.remove(u);
			Q.add(u); // Reinsertar u en Q y heapify
		}
		
		return S;
	}

	@Override
	public MaximizationAlgorithm instance() {
		return new CelfPlusPlusAlgorithm();
	}

}
