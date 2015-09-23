package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import struct.Edge;
import struct.SocialNetwork;
import struct.Node;

public class SimpleFileParser extends FileParser {

	@Override
	public void parseFile(File file, SocialNetwork sn) {
		System.out.println("[SimpleFileParser]");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null ){	
				// Las lineas con # al comienzo son comentarios
				if (! line.startsWith("#")){
					StringTokenizer tokenizer = new StringTokenizer(line);

					// Cada linea valida tiene dos enteros FromNodeId y ToNodeId
					if (tokenizer.countTokens() == 2){
						Integer id1 = Integer.parseInt(tokenizer.nextToken());
						Node n1 = new Node(id1);
						
						Integer id2 = Integer.parseInt(tokenizer.nextToken());
						Node n2 = new Node(id2);
						
						sn.addNode(n1);
						sn.addNode(n2);
						sn.addEdge(new Edge(n1, n2));
					} else {
						System.err.println(" - Linea invalida");
					}
				}
			}

			br.close();
			
			System.out.println(" Archivo parseado correctamente");
			System.out.println(" - Vertices: " + sn.getNodeCount());
			System.out.println(" - Aristas: " + sn.getEdgeCount());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
