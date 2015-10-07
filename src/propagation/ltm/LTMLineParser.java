package propagation.ltm;

import java.util.StringTokenizer;

import parser.LineParser;
import struct.Edge;
import struct.SocialNetwork;
import struct.Vertex;

public class LTMLineParser extends LineParser {

	@Override
	public void parseLine(SocialNetwork sn, String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		
		// Cada linea valida tiene dos enteros FromNodeId y ToNodeId
		// 
		if (tokenizer.countTokens() >= 2){			
			// Vertice 1
			Integer id1 = Integer.parseInt(tokenizer.nextToken());
			Vertex n1 = sn.getVertex(id1);
			if (n1 == null){
				n1 = new LTMVertex(id1, Math.random());
				sn.addVertex(n1);
			}
			
			// Vertice 2
			Integer id2 = Integer.parseInt(tokenizer.nextToken());
			Vertex n2 = sn.getVertex(id2);
			if (n2 == null){
				n2 = new LTMVertex(id2, Math.random());
				sn.addVertex(n2);
			}
			
			// Peso (opcional, puede generarse aleatoriamente)
			if (tokenizer.hasMoreTokens()){
				Double weight = Double.parseDouble(tokenizer.nextToken());
				sn.addEdge(new LTMEdge(n1, n2, weight));
			} else {
				// Arista			
				sn.addEdge(new LTMEdge(n1, n2));
			}
			
		} else {
			System.err.println(" - Linea invalida");
		}
	}

}
