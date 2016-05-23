package propagation;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import struct.Edge;
import struct.Vertex;
import struct.SocialNetwork;

public class IndependentCascadeModel extends PropagationModel {
	
	static Logger log = Logger.getLogger(IndependentCascadeModel.class.getName());
	
	protected Set<Vertex> actives;
	protected Stack<Vertex> target;
	
	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet) {
		log.trace("[Modelo de Propagacion Independent Cascade]");
		
		actives = new HashSet<Vertex>();
		target = new Stack<Vertex>();
		
		// Agregar a lista de targets las semillas iniciales
		for (Vertex node : sn.getVertices()){			
			if (seedSet.contains(node)){
				log.trace(" - Activar " + node);
				target.add(node);
			}
		}
		
		// Procesar, de a uno por vez, los elementos de la lista de targets
		while (step()) {			  
			log.trace("Activos procesados: " + actives.size());
        }
		
		log.trace("Propagacion total:" + actives.size());
		
		return actives.size();
	}

	@Override
	public Boolean step() {
		// Tomar un nodo de los targets y pasar a activados
		Vertex node = target.pop();
		actives.add(node);
		
		// Acceder a los nodos sobre los cuales tiene influencia
		Set<Edge> neighbors = node.getOutNeighbors();
		
		for (Edge edge : neighbors){
			Double random = Math.random();
			Vertex neighbor = edge.getB();
			
			// Si el vecino aun no esta activado y el peso de la arista es mayor al valor random, activar
			if ( !actives.contains(neighbor) && random <= edge.getWeight() ){
				log.trace(" - Activar " + neighbor);
				target.push(neighbor);
			}
		}
		
		return target.size() > 0;
	}

}
