package propagation.ltm;

import struct.Vertex;

public class LTMVertex extends Vertex {

	private Double threshold;

	public LTMVertex(Integer id, Double threshold){
		super (id);
		
		this.threshold = threshold;
	}
	
	public Double getThreshold() {
		return threshold;
	}

	@Override
	public String toString() {
		return super.toString() + " (" + threshold + ")";
	}
	
}
