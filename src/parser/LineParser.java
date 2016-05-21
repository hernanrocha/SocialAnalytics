package parser;

import struct.SocialNetwork;
import struct.Vertex;

public abstract class LineParser {

	public abstract void parseLine (SocialNetwork sn, String line);
	
}
