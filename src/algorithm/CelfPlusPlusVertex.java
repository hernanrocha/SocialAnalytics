package algorithm;

import struct.Vertex;

public class CelfPlusPlusVertex implements Comparable<CelfPlusPlusVertex> {
	Vertex vertex;
	Double mg1; // marginal(S, u)
	CelfPlusPlusVertex prev_best; // Nodo con la mayor ganancia marginal de todos los usuarios examinados en la iteracion actual
	Double mg2; // marginal(S U {prev_best}, u)
	Integer flag; // Iteracion en que u.mg1 fue actualizado			
	
	public CelfPlusPlusVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	@Override
	public int compareTo(CelfPlusPlusVertex arg0) {
		return 0;
	}

}
