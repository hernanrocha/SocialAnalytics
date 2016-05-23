package propagation;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import struct.Edge;
import struct.SocialNetwork;
import struct.Vertex;

public class LinearThresholdModel extends PropagationModel {
	
	static Logger log = Logger.getLogger(LinearThresholdModel.class.getName());
	
	protected Set<Vertex> actives, inactives;

	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet) {
		log.trace("[Modelo de Propagacion Linear Threshold]");
		
		actives = new HashSet<Vertex>();
		inactives = new HashSet<Vertex>();
		
		for (Vertex node : sn.getVertices()){			
			if (seedSet.contains(node)){
				// Agregar los seeds a la lista de nodos activos
				actives.add((Vertex) node);
			} else {
				// Agregar el resto de los nodos a la lista de inactivos
				inactives.add((Vertex) node);
			}
		}
		
		// Realizar proceso de spreading
		int i = 1;
		do {
			log.trace(" --------------- Step " + i + " ---------------");
			log.trace(" - Activos: " + actives.size());
			log.trace(" - Inactivos: " + inactives.size());
			
			i++;
		} while (step());

		log.trace(" - No se activo ningun nodo. Propagacion finalizada");
		log.trace(" --------------------------------------");
		
		return actives.size();
	}

	@Override
	public Boolean step() {
		Set<Vertex> newActives = new HashSet<Vertex>();
		
		// Buscar nuevos nodos para activar
		for (Vertex node : inactives){
			Set<Edge> neighbors = node.getInNeighbors();
			Double influence = 0.0;

			// Sumar la influencia de los nodos vecinos ya activos
			for (Edge edge : neighbors){
				if (actives.contains(edge.getA())){
					influence += edge.getWeight();
				}
			}
			
			// Si superan el threshold del nodo, pasar a lista de nodos a activar
			if (influence > node.getThreshold()){				
				log.trace(influence + " > " + node.getThreshold() + ". Activo " + node);
				newActives.add(node);			
			}
		}
		
		// Pasar de inactivos a activos
		for (Vertex node : newActives){
			inactives.remove(node);
			actives.add(node);
		}
		
		// Si se activo algun nodo, es necesario seguir el proceso
		return !newActives.isEmpty();
	}

}
