package com.ibm.checksumr.agent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScannableFilesManager {
	public static Path[] toPaths(String[] stringsOfPath) {
		Path[] paths = new Path[stringsOfPath.length];

		for (int i = 0; i < stringsOfPath.length; i++) {
			paths[i] = Paths.get(stringsOfPath[i]);
		}

		return paths;
	}

	public static List<Path> listFilepaths(Path... pathsToSearch) {
		List<Path> directoryListing = new ArrayList<Path>();

		for (Path pathToSearch : pathsToSearch) {
			try (Stream<Path> stream = Files.walk(pathToSearch)) {
				directoryListing.addAll(stream.filter(Files::isRegularFile).collect(Collectors.toList()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return directoryListing;
	}

}
