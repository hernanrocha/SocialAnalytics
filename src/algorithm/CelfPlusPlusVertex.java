package algorithm;

import struct.Vertex;

public class CelfPlusPlusVertex implements Comparable<CelfPlusPlusVertex> {
	Vertex vertex;
	Double mg1; 					// marginal(S, u)
	CelfPlusPlusVertex prev_best; 	// Nodo con la mayor ganancia marginal de todos los usuarios examinados en la iteracion actual
	Double mg2; 					// marginal(S U {prev_best}, u)
	Integer flag; 					// Iteracion en que u.mg1 fue actualizado			
	
	public CelfPlusPlusVertex(Vertex vertex) {
		this.vertex = vertex;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
//		if (getClass() != obj.getClass())
//			return false;
		CelfPlusPlusVertex other = (CelfPlusPlusVertex) obj;
//		if (vertex == null) {
//			if (other.vertex != null)
//				return false;
		if (!vertex.equals(other.vertex))
			return false;
		
		return true;
	}

	@Override
	public int compareTo(CelfPlusPlusVertex other) {
		int comp = mg1.compareTo(other.mg1);
		
		if (comp == 0) {
			return vertex.getID().compareTo(other.vertex.getID()) * -1;
		}
		
		return comp;
	}

}
