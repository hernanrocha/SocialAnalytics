package propagation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import gui.GraphViz;
import struct.Edge;
import struct.SocialNetwork;
import struct.Vertex;

public class LinearThresholdModel extends PropagationModel {
	
	static Logger log = Logger.getLogger(LinearThresholdModel.class.getName());
	
	//protected Set<Vertex> actives, inactives;
	protected Set<Vertex> actives, nodes;

	@Override
	public Integer propagate(SocialNetwork sn, Set<Vertex> seedSet, boolean drawGraph) {
		log.trace("[Modelo de Propagacion Linear Threshold]");
		
		this.drawGraph = drawGraph;
		
		actives = new HashSet<Vertex>();

		for (Vertex v : seedSet) {
			actives.add(v);
			
			if (drawGraph) {
				GraphViz.getInstance().addln(v.getID().toString());				
			}
		}
		nodes = sn.getVertices();
		
		// Realizar proceso de spreading
		int i = 1;
		do {
			log.trace("--------------- Step " + i + " ---------------");
			log.trace("- Activos: " + actives.size());
			
			i++;
		} while (step());

		log.trace("No se activo ningun nodo. Propagacion finalizada");
		log.trace("Propagacion total: " + actives.size());
		log.trace("--------------------------------------");
		
		return actives.size();
	}

	@Override
	public Boolean step() {
		Set<Vertex> newActives = new HashSet<Vertex>();
		
		// Buscar nuevos nodos para activar
		
		for (Vertex node : nodes){
			if (!actives.contains(node)) {
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
					log.trace("(Activar " + node + ") " + influence + " > " + node.getThreshold());
					
					if (drawGraph) {
						for (Edge edge : neighbors){
							if (actives.contains(edge.getA())){
								NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
								DecimalFormat df = (DecimalFormat) nf;
								df.applyPattern("#.##");
								String weight = df.format(edge.getWeight());
								GraphViz.getInstance().addln(edge.getA().getID().toString() + " -> " + edge.getB().getID().toString() +
										"[ label = " + weight + " ]");
							}
						}
					}
					
					newActives.add(node);
				}
			}
		}
		
		// Pasar de inactivos a activos
		for (Vertex node : newActives){
			//inactives.remove(node);
			actives.add(node);
		}
		
		// Si se activo algun nodo, es necesario seguir el proceso
		return !newActives.isEmpty();
	}

}
