package com.ibm.checksumr.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
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

		Set<MessageDigest> algorithms = new HashSet<MessageDigest>(2, 1);
		algorithms.add(MessageDigest.getInstance("MD5"));
		algorithms.add(MessageDigest.getInstance("SHA1"));

		HashComputer hashComputer = new HashComputer(algorithms);
		FileHashCalculation result = hashComputer.calculate(fileToCheck);

		assertEquals(pathString, result.getFilePath());
		assertEquals(2, result.getChecksums().size());
		assertEquals("83ec6d9c9babf43e995c42d64bf9453c", result.getChecksums().get("MD5"));
		assertEquals("b05702e0fa7938ca0a4e91c21ce1c5bdf20f191b", result.getChecksums().get("SHA1"));
	}
}
