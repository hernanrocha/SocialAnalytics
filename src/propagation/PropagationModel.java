package propagation;

import java.util.Set;

import struct.SocialNetwork;
import struct.Node;

public abstract class PropagationModel {
	
	public abstract void propagate(SocialNetwork sn, Set<Node> seedSet);
	
	public abstract Boolean step();
}
