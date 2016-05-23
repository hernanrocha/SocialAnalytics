package parser;

import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import struct.Edge;
import struct.SocialNetwork;
import struct.Vertex;

public class FbCircleLineParser extends LineParser {

	static Logger log = Logger.getLogger(FbCircleLineParser.class.getName());

	@Override
	public void parseLine(SocialNetwork sn, String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		
		if (!tokenizer.hasMoreTokens())
			return;
		
		log.trace("Circulo: " + tokenizer.nextToken());
		
		Vector<Integer> members = new Vector<Integer>();
		while(tokenizer.hasMoreTokens()){
			members.add(Integer.parseInt(tokenizer.nextToken()));
		}
		
		log.trace(" " + members.size() + " elemento(s)");
		
		// Crear vinculo entre todos los elementos del circulo
		for (int i = 0; i < members.size(); i++){
			for (int j = i + 1; j < members.size(); j++) {
				Vertex v1 = sn.addVertex(new Vertex(members.elementAt(i)));
				Vertex v2 = sn.addVertex(new Vertex(members.elementAt(j)));
				
				// Se influencian mutuamente
				sn.addEdge(new Edge(v1, v2));
				sn.addEdge(new Edge(v2, v1));
			}
		}
		
	}

}
