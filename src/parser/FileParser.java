package parser;

import java.io.File;

import struct.SocialNetwork;

/***
 * 
 * @author Hernan
 * Clase abstracta que carga una red social a partir de un archivo
 *
 */
public abstract class FileParser {

	public abstract void parseFile(File file, SocialNetwork sn);
	
}
