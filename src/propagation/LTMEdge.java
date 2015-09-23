package propagation;

import struct.Edge;

public class LTMEdge extends Edge {

	private Double weight;

	public LTMEdge(Edge edge, Double weight){
		super(edge.getA(), edge.getB());
		
		this.weight = weight;
	}

	public Double getWeight() {
		return weight;
	}

}
