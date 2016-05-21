package parser;

import java.io.File;

import struct.SocialNetwork;

/***
 * 
 * Clase abstracta que carga una red social a partir de un archivo.
 * 
 * @author Hernan Rocha
 *
 */
public abstract class FileParser {

	public abstract void parseFile(File file, LineParser lp, SocialNetwork sn);
	
}
