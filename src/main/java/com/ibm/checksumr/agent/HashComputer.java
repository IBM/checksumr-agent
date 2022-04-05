package com.ibm.checksumr.agent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashComputer {
	private Set<MessageDigest> algorithms;

	public HashComputer(Set<MessageDigest> algorithms) {
		this.algorithms = algorithms;
	}

	public Map<String, Map<String, String>> calculate(Path fileToCheck) {
		byte[] digest;
		String scannedPath = fileToCheck.toAbsolutePath().toString();
		Map<String, String> checksumMap = new HashMap<String, String>(this.algorithms.size());
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>(1);

		try {
			byte[] fileContent = Files.readAllBytes(fileToCheck);

			for (MessageDigest algorithm : this.algorithms) {
				algorithm.update(fileContent);
				digest = algorithm.digest();

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < digest.length; i++) {
					sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
				}

				checksumMap.put(algorithm.getAlgorithm(), sb.toString());
			}

			result.put(scannedPath, checksumMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
