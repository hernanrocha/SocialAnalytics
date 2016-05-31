package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import struct.SocialNetwork;

/**
 * Implementacion de FileParser basico. 
 * Las lineas que comienzan con un '#' son consideradas como un comentario y no son analizadas.
 * 
 * @author Hernan Rocha
 *
 */
public class SimpleFileParser extends FileParser {

	static Logger log = Logger.getLogger(SimpleFileParser.class.getName());
	
	@Override
	public boolean parseFile(File file, LineParser lp, SocialNetwork sn) {
		log.info("Archivo: " + file.getName());
		
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
			
			return true;
		} catch (FileNotFoundException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return false;
	}
}
