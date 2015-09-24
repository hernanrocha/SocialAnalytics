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
	
	public void addVertex(Vertex vertex) {
		vertices.put(vertex.getID(), vertex);		
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
