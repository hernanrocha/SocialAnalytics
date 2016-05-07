package propagation;

import java.util.Set;

import struct.SocialNetwork;
import struct.Vertex;

public abstract class PropagationModel {
	
	// Dada una red social y un conjunto semilla, calcular el conjunto de vertices
	// a los que se propaga
	public abstract Integer propagate(SocialNetwork sn, Set<Vertex> seedSet);
	
	// Realizar un paso de propagacion. Devuelve true si se debe continuar
	public abstract Boolean step();
}
