package algorithm;

import struct.Vertex;

public class CelfVertex implements Comparable<CelfVertex> {
	Vertex vertex;
	Double marginal;

	public CelfVertex(Vertex vertex, Double marginal) {
		this.vertex = vertex;
		this.marginal = marginal;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public Double getMarginal() {
		return marginal;
	}

	@Override
	public int compareTo(CelfVertex other) {
		
		int comp = marginal.compareTo(other.getMarginal());
		
		if (comp == 0) {
			return vertex.getID().compareTo(other.getVertex().getID()) * -1;
		}
		
		return comp;
	}

}
