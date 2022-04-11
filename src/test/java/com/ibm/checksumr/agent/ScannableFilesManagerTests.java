package com.ibm.checksumr.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

@SpringBootTest
public class ScannableFilesManagerTests {
	static Faker faker;

	@BeforeAll
	static void initialize() throws IOException {
		faker = new Faker();

	}

	@Test
	void whenConvertingAnArrayOfStringsToAnArrayOfPathsAllPathElementsMustHaveTheSameValuesAsTheStrings() {
		int numberOfItems = faker.random().nextInt(1, 10);
		String[] stringsOfPath = new String[numberOfItems];
		for (int i = 0; i < numberOfItems; i++) {
			stringsOfPath[i] = faker.app().name();
		}

		Path[] arrayOfPath = ScannableFilesManager.toPaths(stringsOfPath);

		assertEquals(numberOfItems, arrayOfPath.length);
		for (int i = 0; i < stringsOfPath.length; i++) {
			assertTrue(arrayOfPath[i].endsWith(stringsOfPath[i]));
		}
	}

	@Test
	void ensureAllAbsoluteFilePathsFromAGivenPathListAreListed() throws IOException {
		Path rootReference, fakeFileReference;
		List<Path> listOfPaths = new ArrayList<Path>();
		FileSystem virtualFileSystem = Jimfs.newFileSystem(Configuration.unix());

		Path app1BaseDir = virtualFileSystem.getPath(faker.app().name());
		Path app2BaseDir = virtualFileSystem.getPath(faker.app().name());
		Files.createDirectory(app1BaseDir);
		Files.createDirectory(app2BaseDir);

		for (int i = 0; i < 10; i++) {
			rootReference = app1BaseDir;
			if (i % 2 == 0) {
				rootReference = app2BaseDir;
			}

			fakeFileReference = rootReference.resolve(faker.file().fileName("", null, null, ""));
			listOfPaths.add(fakeFileReference);
			Files.createFile(fakeFileReference);
		}
		Collections.sort(listOfPaths);

		List<Path> directoryListing = ScannableFilesManager.listFilepaths(app1BaseDir, app2BaseDir);
		Collections.sort(directoryListing);

		for (int i = 0; i < listOfPaths.size(); i++) {
			assertEquals(0, directoryListing.get(i).compareTo(listOfPaths.get(i)));
		}
	}
}
