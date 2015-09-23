package struct;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class EdgeSet {	

	public Map<Node, Set<Edge>> edges, inverse;
	
	public EdgeSet(){
		edges = new HashMap<Node, Set<Edge>>();
		inverse = new HashMap<Node, Set<Edge>>();
	}
	
	public void addEdge(Edge edge){
		Node a = edge.getA();
		Node b = edge.getB();
		
		if (edges.containsKey(a)){
			// 'a' ya tiene aristas			
			edges.get(a).add(edge);
		}else{
			// 'a' todavia no tiene aristas
			Set<Edge> set = new HashSet<Edge>();
			set.add(edge);
			edges.put(a, set);
		}
		
		if (inverse.containsKey(b)){
			// 'b' ya tiene aristas	
			inverse.get(b).add(edge);
		}else{
			// 'b' todavia no tiene aristas
			Set<Edge> set = new HashSet<Edge>();
			set.add(edge);
			inverse.put(b, set);
		}
		
	}
	
	@Override
	public String toString() {
		String st = "[EDGE-SET]" + System.lineSeparator();
		for (Entry<Node, Set<Edge>> entry : edges.entrySet()){
			st += entry.getKey() + ":";
			for (Edge edge : entry.getValue()){
				st += " " + edge.getB();
			}
			st += System.lineSeparator();
		}
		
		return st;
	}

	public Integer getEdgeCount() {
		int edgeCount = 0;

		// Contar la cantidad de aristas y dividir por dos (no es dirigido)
		for (Entry<Node, Set<Edge>> entry : edges.entrySet()){
			edgeCount += entry.getValue().size();
		}
		
		return edgeCount;
	}
	
	public Map<Node, Set<Edge>> getEdges(){
		return edges;
	}
	
	public Map<Node, Set<Edge>> getInverseEdges(){
		return inverse;
	}
}
