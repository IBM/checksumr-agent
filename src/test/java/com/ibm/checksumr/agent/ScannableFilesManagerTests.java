package com.ibm.checksumr.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

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
}
