package com.ibm.checksumr.agent;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ScannableFilesManager {
	
	public static Path[] toPaths(String[] stringsOfPath) {
		Path[] paths = new Path[stringsOfPath.length];

		for (int i = 0; i < stringsOfPath.length; i++) {
			paths[i] = Paths.get(stringsOfPath[i]);
		}

		return paths;
	}

}
