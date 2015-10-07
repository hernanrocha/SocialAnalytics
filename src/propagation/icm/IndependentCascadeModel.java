package propagation.icm;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import propagation.PropagationModel;
import propagation.ltm.LTMEdge;
import propagation.ltm.LTMVertex;
import struct.Edge;
import struct.Vertex;
import struct.SocialNetwork;

public class IndependentCascadeModel extends PropagationModel {
	protected Set<LTMVertex> actives;
	protected Stack<LTMVertex> target;
	
	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet) {
		// TODO Auto-generated method stub
		System.out.println("[Modelo de Propagacion Independent Cascade]");
		
		actives = new HashSet<LTMVertex>();
		target = new Stack<LTMVertex>();
		
		// Agregar a lista de activos e inactivos
		for (Vertex node : sn.getVertices()){			
			// Agregar a la lista de nodos activos/inactivos
			if (seedSet.contains(node)){
				target.add((LTMVertex) node);
			}
		}
		
		while (target.size() > 0) {
			LTMVertex node = target.pop();
			actives.add(node);
			
			// Acceder a los nodos sobre los cuales tiene influencia
			Set<Edge> neighbors = node.getOutNeighbors();
			
			for (Edge edge : neighbors){
				Double random = Math.random();
				LTMVertex neighbor = (LTMVertex) edge.getB();
				if ( !actives.contains(neighbor) && random <= ((LTMEdge) edge).getWeight() ){
					System.out.println("Activo a " + neighbor);
					target.push(neighbor);
				}
			}    
			System.out.println(" - Activos: " + actives.size());
        }
		
		return actives.size();
	}

	@Override
	public Boolean step() {
		// TODO Auto-generated method stub
		return null;
	}

}
