package struct;

import java.util.HashSet;
import java.util.Set;

/***
 * 
 * @author Hernan
 * Representa un usuario de la red social
 * Se identifica mediante el campo Integer id
 *
 */
public class Vertex {

	protected Integer id;
	protected Double threshold;
	protected Set<Edge> inNeighbors = new HashSet<Edge>();
	protected Set<Edge> outNeighbors = new HashSet<Edge>();

	public Vertex(Integer id, Double threshold){
		this.id = id;
		this.threshold = threshold;
	}
	
	public Vertex(Integer id) {
		this(id, Math.random());
	}

	// Neighbors	
	public void addNeighbor(Edge edge) {
		if (edge.getA().equals(this)){
			outNeighbors.add(edge);
		} else {
			inNeighbors.add(edge);
		}
	}
	
	public Set<Edge> getInNeighbors() {
		return inNeighbors;
	}

	public Set<Edge> getOutNeighbors() {
		return outNeighbors;
	}

	public Integer getID() {
		return id;
	}
	
	public Double getThreshold() {
		return threshold;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// Verificar que sea subclase
		//if (getClass() != obj.getClass())
		//	return false;
		Vertex other = (Vertex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "" + id;
	}

}
