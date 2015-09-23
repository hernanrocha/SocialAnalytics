package struct;

import java.util.HashSet;
import java.util.Set;

/***
 * 
 * @author Hernan
 * Clase que almacena los datos de la red social
 *
 */
public class SocialNetwork {

	protected Set<Node> nodes = new HashSet<Node>();
	protected EdgeSet edges = new EdgeSet();

	public SocialNetwork() {
		super();
	}
	
	public Integer getNodeCount() {
		return nodes.size();
	}
	
	public Integer getEdgeCount() {
		return edges.getEdgeCount();
	}
	
	public void setEdgeSet(EdgeSet edgeSet) {
		this.edges = edgeSet;
	}

	public void addNode(Node node) {
		this.nodes.add(node);		
	}

	public void addEdge(Edge edge) {
		this.edges.addEdge(edge);
	}

	public Set<Node> getNodes(){
		return nodes;
	}
	
	public EdgeSet getEdgeSet(){
		return edges;
	}
}
