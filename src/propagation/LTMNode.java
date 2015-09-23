package propagation;

import struct.Node;

public class LTMNode extends Node {

	//private Node node;
	private Double threshold;

	public LTMNode(Node node, Double threshold){
		super (node.getID());
		
		this.threshold = threshold;
	}

	/*public Node getNode() {
		return node;
	}*/

	public Double getThreshold() {
		return threshold;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + threshold + ")";
	}
	
}
