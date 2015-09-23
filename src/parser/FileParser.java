package parser;

import java.io.File;

import struct.SocialNetwork;

public abstract class FileParser {

	public abstract void parseFile(File file, SocialNetwork sn);
	
}
