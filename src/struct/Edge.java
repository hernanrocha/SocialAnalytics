package struct;

public class Edge {

	protected Vertex a, b;
	protected Double weight;
	
	public Edge(Vertex a, Vertex b, Double weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	}

	public Edge(Vertex a, Vertex b) {
		this.a = a;
		this.b = b;
	}

	public Vertex getA() {
		return a;
	}

	public Vertex getB() {
		return b;
	}
	
	public Double getWeight() {
		if (weight != null){
			// El peso se especifico por archivo
			return weight;
		}
		
		// El peso es 1 / la cantidad de vecinos que influyen sobre el
		// Se calcula en cada llamada ya que la cantidad de vecinos podria variar
		return 1 / (double) b.getInNeighbors().size();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + a + ", " + b + ")";
	}
	
}
