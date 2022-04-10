package com.ibm.checksumr.agent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Set;

public class HashComputer {
	private Set<MessageDigest> algorithms;

	public HashComputer(Set<MessageDigest> algorithms) {
		this.algorithms = algorithms;
	}

	public FileHashCalculation calculate(Path fileToCheck) {
		byte[] fileContent;
		String algorithmName, checksum;
		FileHashCalculation result = new FileHashCalculation(fileToCheck);

		try {
			fileContent = Files.readAllBytes(fileToCheck);

			for (MessageDigest algorithm : this.algorithms) {
				algorithmName = algorithm.getAlgorithm();
				checksum = this.generateChecksum(algorithm, fileContent);

				result.getChecksums().put(algorithmName, checksum);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private String generateChecksum(MessageDigest algorithm, byte[] data) {
		byte[] digest;

		algorithm.update(data);
		digest = algorithm.digest();
		return BytesTranslator.toHex(digest);
	}

}
