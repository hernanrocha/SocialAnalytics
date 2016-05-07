package propagation.ltm;

import java.util.HashSet;
import java.util.Set;

import propagation.PropagationModel;
import struct.Edge;
import struct.SocialNetwork;
import struct.Vertex;

public class LinearThresholdModel extends PropagationModel {
	protected Set<LTMVertex> actives, inactives;

	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet) {
		System.out.println("[Modelo de Propagacion Linear Threshold]");
		
		actives = new HashSet<LTMVertex>();
		inactives = new HashSet<LTMVertex>();
		
		for (Vertex node : sn.getVertices()){			
			if (seedSet.contains(node)){
				// Agregar los seeds a la lista de nodos activos
				actives.add((LTMVertex) node);
			} else {
				// Agregar el resto de los nodos a la lista de inactivos
				inactives.add((LTMVertex) node);
			}
		}
		
		// Realizar proceso de spreading
		int i = 1;
		do {
			System.out.println(" --------------- Step " + i + " ---------------");
			System.out.println(" - Activos: " + actives.size());
			System.out.println(" - Inactivos: " + inactives.size());
			
			i++;
		} while (step());

		System.out.println(" - No se activo ningun nodo. Propagacion finalizada");
		System.out.println(" --------------------------------------");
		
		return actives.size();
	}

	@Override
	public Boolean step() {
		Set<LTMVertex> newActives = new HashSet<LTMVertex>();
		
		// Buscar nuevos nodos para activar
		for (LTMVertex node : inactives){
			Set<Edge> neighbors = node.getInNeighbors();
			Double influence = 0.0;
			
			for (Edge edge : neighbors){
				// Sumar la influencia de los nodos vecinos ya activos
				if (actives.contains(edge.getA())){
					influence += edge.getWeight();
				}
			}
			
			if (influence > node.getThreshold()){				
				System.out.println(influence + " > " + node.getThreshold() + ". Activo " + node);
				newActives.add(node);			
			}
		}
		
		// Pasar de inactivos a activos
		for (LTMVertex node : newActives){
			inactives.remove(node);
			actives.add(node);
		}
		
		// Si se activo algun nodo, es necesario seguir el proceso
		return !newActives.isEmpty();
	}

}
