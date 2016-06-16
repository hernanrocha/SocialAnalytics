package propagation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import gui.GraphViz;
import struct.Edge;
import struct.Vertex;
import struct.SocialNetwork;

public class IndependentCascadeModel extends PropagationModel {
	
	static Logger log = Logger.getLogger(IndependentCascadeModel.class.getName());
	
	protected Set<Vertex> actives;
	protected Stack<Vertex> target;
	
	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet, boolean drawGraph) {
		log.trace("[Modelo de Propagacion Independent Cascade]");
		
		actives = new HashSet<Vertex>();
		target = new Stack<Vertex>();
		
		// Agregar a lista de targets las semillas iniciales
		for (Vertex node : sn.getVertices()){			
			if (seedSet.contains(node)){
				log.trace("Seed: " + node);
				target.add(node);
				
				if (drawGraph) {
					GraphViz.getInstance().addln(node.getID().toString());				
				}
			}
		}
		
		// Procesar, de a uno por vez, los elementos de la lista de targets
		int i = 1;
		do {
			log.trace("--------------- Step " + i + " ---------------");
			log.trace("- Activos: " + actives.size());
			log.trace("- Targets: " + target.size());

			i++;
		} while (step());

		log.trace("--------------------------------------");
		log.trace("No quedan mas nodos a activar. Propagacion finalizada");
		log.trace("Propagacion total: " + actives.size());
		log.trace("--------------------------------------");
		
		return actives.size();
	}

	@Override
	public Boolean step() {
		// Tomar un nodo de los targets y pasar a activados
		Vertex node = target.pop();
		actives.add(node);

		log.trace("Procesando: " + node);
		
		// Acceder a los nodos sobre los cuales tiene influencia
		Set<Edge> neighbors = node.getOutNeighbors();
		
		for (Edge edge : neighbors){
			Double random = Math.random();
			Vertex neighbor = edge.getB();
			
			// Si el vecino aun no esta activado ni en target y el peso de la arista es mayor al valor random, activar
			if ( !actives.contains(neighbor) && !target.contains(neighbor) && random <= edge.getWeight() ){
				log.trace(" - Agregar a Target " + neighbor + "(" + random + " <= " + edge.getWeight() + ")");
				target.push(neighbor);
				
				if (drawGraph) {
					NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
					DecimalFormat df = (DecimalFormat) nf;
					df.applyPattern("#.##");
					String weight = df.format(edge.getWeight());
					GraphViz.getInstance().addln(edge.getA().getID().toString() + " -> " + edge.getB().getID().toString() +
							"[ label = " + weight + " ]");
				}
			}
		}
		
		return target.size() > 0;
	}

}
