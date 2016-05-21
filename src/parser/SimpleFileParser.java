package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import struct.SocialNetwork;

/**
 * @author HERNAN
 * Implementacion de FileParser basico.
 * Las lineas que comienzan con un '#' son consideradas como un comentario y no son analizadas.
 *
 */
public class SimpleFileParser extends FileParser {

	static Logger log = Logger.getLogger(SimpleFileParser.class.getName());
	
	LineParser lp = new SimpleLineParser();
	
	@Override
	public void parseFile(File file, SocialNetwork sn) {
		log.info(" Archivo: " + file.getName());
		System.out.println("[SimpleFileParser]");
		System.out.println();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null ){	
				// Las lineas con # al comienzo son comentarios
				if (! line.startsWith("#")){
					lp.parseLine(sn, line);
				}
			}

			br.close();
			
			System.out.println(" Archivo parseado correctamente");
			System.out.println(" - Vertices: " + sn.getVerticesCount());
			System.out.println(" - Aristas: " + sn.getEdgeCount());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
