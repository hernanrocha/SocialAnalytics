package propagation;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import struct.Edge;
import struct.EdgeSet;
import struct.SocialNetwork;
import struct.Node;

public class LinearThresholdModel extends PropagationModel {
	protected Set<LTMNode> actives, inactives;
	protected EdgeSet ltmEdges;

	@Override
	public void propagate(SocialNetwork sn, Set<Node> seedSet) {
		System.out.println("[Modelo de Propagacion Linear Threshold]");
		
		actives = new HashSet<LTMNode>();
		inactives = new HashSet<LTMNode>();
		
		for (Node node : sn.getNodes()){
			// Crear LTMNode con un threshold aleatorio
			LTMNode ltmNode = new LTMNode(node, Math.random());
			System.out.println(ltmNode);
			
			// Agregar a la lista de nodos activos/inactivos
			if (seedSet.contains(node)){
				actives.add(ltmNode);
			} else {
				inactives.add(ltmNode);
			}
		}
		
		System.out.println(" - Activos: " + actives.size());
		System.out.println(" - Inactivos: " + inactives.size());
		
		Map<Node, Set<Edge>> edges = sn.getEdgeSet().getInverseEdges();
		ltmEdges = new EdgeSet();
		
		// Recorrer la lista invertida
		for (Entry<Node, Set<Edge>> entry : edges.entrySet()){			
			// Calcular peso (1 / [Cantidad de nodos que influyen sobre el])
			Double weight =  1 / (double) entry.getValue().size();
			
			for (Edge edge : entry.getValue()){
				ltmEdges.addEdge(new LTMEdge(edge, weight));
				
				System.out.println(edge + " - Peso " + weight);
			}
			
		}
		
		step();
	}

	@Override
	public Boolean step() {
		Set<LTMNode> newActives = new HashSet<LTMNode>();
		
		// Buscar nuevos nodos para activar
		for (LTMNode node : inactives){
			Set<Edge> neighbors = ltmEdges.getInverseEdges().get(node);
			Double influence = 0.0;
			
			if (neighbors != null){
				for (Edge edge : neighbors){
					// Sumar la influencia de los nodos vecinos ya activos
					if (actives.contains(edge.getA())){
						influence += ((LTMEdge) edge).getWeight();
					}
				}
			}
			
			if (influence > node.getThreshold()){				
				System.out.println(influence + " > " + node.getThreshold() + ". Activo " + node);
				newActives.add(node);			
			}
		}
		
		// Pasar de inactivos a activos
		for (LTMNode node : newActives){
			inactives.remove(node);
			actives.add(node);
		}
		
		System.out.println(" - Activos: " + actives.size());
		System.out.println(" - Inactivos: " + inactives.size());
		
		return newActives.isEmpty();
	}

}
