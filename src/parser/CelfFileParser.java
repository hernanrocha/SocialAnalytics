package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import struct.SocialNetwork;

/**
 * Implementacion de FileParser para datasets del algoritmo CELF. 
 * La primera linea es considerada como un comentario y no es analizada.
 * 
 * @author Hernan Rocha
 *
 */
public class CelfFileParser extends FileParser {

	static Logger log = Logger.getLogger(CelfFileParser.class.getName());
	
	@Override
	public boolean parseFile(File file, LineParser lp, SocialNetwork sn) {
		log.info("Archivo: " + file.getName());
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			// No leer la primera linea
			br.readLine();
			
			while((line = br.readLine()) != null ){
				lp.parseLine(sn, line);
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
