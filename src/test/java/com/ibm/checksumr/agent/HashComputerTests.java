package com.ibm.checksumr.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

@SpringBootTest
public class HashComputerTests {
	@Test
	void aValidAlgorithmMustGenerateAMapOfChecksumHashStringsForAFile() throws NoSuchAlgorithmException, IOException {
		FileSystem fakeFilesystem = Jimfs.newFileSystem(Configuration.unix());
		Path fileToCheck = fakeFilesystem.getPath("/").resolve("file.txt");
		String pathString = fileToCheck.toAbsolutePath().toString();
		Files.write(fileToCheck, ("It's only a text!").getBytes());

		Set<MessageDigest> algorithms = new HashSet<MessageDigest>(1);
		MessageDigest md5digest = MessageDigest.getInstance("MD5");
		algorithms.add(md5digest);

		HashComputer hashComputer = new HashComputer(algorithms);
		Map<String, Map<String, String>> result = hashComputer.calculate(fileToCheck);

		Map<String, String> hashResult = new HashMap<String, String>(1);
		hashResult.put("MD5", "83ec6d9c9babf43e995c42d64bf9453c");
		Map<String, Map<String, String>> expected = new HashMap<String, Map<String, String>>(1);
		expected.put(pathString, hashResult);

		assertEquals(expected.get(pathString), result.get(pathString));
	}
}
