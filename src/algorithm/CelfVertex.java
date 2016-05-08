package algorithm;

import struct.Vertex;

public class CelfVertex {
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

}
