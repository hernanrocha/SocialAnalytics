package propagation.icm;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import propagation.PropagationModel;
import struct.Edge;
import struct.Vertex;
import struct.SocialNetwork;

public class IndependentCascadeModel extends PropagationModel {
	protected Set<Vertex> actives;
	protected Stack<Vertex> target;
	
	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet) {
		//System.out.println("[Modelo de Propagacion Independent Cascade]");
		
		actives = new HashSet<Vertex>();
		target = new Stack<Vertex>();
		
		// Agregar a lista de activos e inactivos
		for (Vertex node : sn.getVertices()){			
			// Agregar a la lista de nodos activos/inactivos
			if (seedSet.contains(node)){
				//System.out.println(" - Activar " + node);
				target.add(node);
			}
		}
		
		while (target.size() > 0) {
			Vertex node = target.pop();
			actives.add(node);
			
			// Acceder a los nodos sobre los cuales tiene influencia
			Set<Edge> neighbors = node.getOutNeighbors();
			
			for (Edge edge : neighbors){
				Double random = Math.random();
				Vertex neighbor = edge.getB();
				if ( !actives.contains(neighbor) && random <= edge.getWeight() ){
					//System.out.println(" - Activar " + neighbor);
					target.push(neighbor);
				}
			}    
			//System.out.println("Activos procesados: " + actives.size());
        }
		
		//System.out.println(" Propagacion total:" + actives.size());
		return actives.size();
	}

	@Override
	public Boolean step() {
		// TODO Auto-generated method stub
		return null;
	}

}
