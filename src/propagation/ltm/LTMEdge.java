package propagation.ltm;

import struct.Edge;
import struct.Vertex;

public class LTMEdge extends Edge {

	private Double weight;

	public LTMEdge(Vertex a, Vertex b) {
		super(a, b);
	}

	public LTMEdge(Vertex a, Vertex b, Double weight) {
		super(a, b);
		
		this.weight = weight;
	}

	public Double getWeight() {
		if (weight != null){
			// El peso se especifico por archivo
			return weight;
		}
		
		// El peso es 1 / la cantidad de vecinos que influyen sobre el
		return 1 / (double) b.getInNeighbors().size();
	}

}
