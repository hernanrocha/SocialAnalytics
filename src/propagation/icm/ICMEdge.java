package propagation.icm;

import struct.Edge;

public class ICMEdge extends Edge {

	protected Double pProbability;
	
	public ICMEdge(Edge edge, Double pProbability){
		super(edge.getA(), edge.getB());
		
		this.pProbability = pProbability;
	}
	
}
