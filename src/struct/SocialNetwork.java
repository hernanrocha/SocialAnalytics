package struct;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/***
 * 
 * @author Hernan
 * Clase que almacena los datos de la red social
 *
 */
public class SocialNetwork {

	protected Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
	protected Set<Edge> edges = new HashSet<Edge>();

	public SocialNetwork() {
		super();
	}
	
	// ------ Vertices -----
	
	public Vertex addVertex(Vertex vertex) {
		Vertex v = vertices.get(vertex.getID());
		if (v == null){
			// Es un vertice nuevo, agregarlo a la lista
			vertices.put(vertex.getID(), vertex);
			return vertex;
		}
		
		// El vertice ya existe
		return v;
	}
	
	public Integer getVerticesCount() {
		return vertices.size();
	}
	
	public Set<Vertex> getVertices(){
		Set<Vertex> vertices = new HashSet<Vertex>();
		vertices.addAll(this.vertices.values());
		
		return vertices;
	}
	
	public Vertex getVertex(Integer id){
		return vertices.get(id);
	}
	
	// ------ Edges -----
	
	public void addEdge(Edge edge) {		
		// Agregar vecinos
		edge.getA().addNeighbor(edge);
		edge.getB().addNeighbor(edge);
		
		// Agregar a lista de vertices
		edges.add(edge);
	}
	
	public Set<Edge> getEdges(){
		return edges;
	}
	
	public Integer getEdgeCount() {
		return edges.size();
	}
	
}
