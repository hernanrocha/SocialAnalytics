package propagation;

import java.util.Set;

import struct.SocialNetwork;
import struct.Vertex;

public abstract class PropagationModel {
	
	public abstract Integer propagate(SocialNetwork sn, Set<Vertex> seedSet);
	
	public abstract Boolean step();
}
