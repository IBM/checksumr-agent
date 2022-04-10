package com.ibm.checksumr.agent;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class FileHashCalculation {
	@Getter
	@Setter
	private String filePath;

	@Getter
	@Setter
	private Map<String, String> checksums = new HashMap<String, String>();

	public FileHashCalculation(Path filePath) {
		this(filePath.toAbsolutePath().toString());
	}

	public FileHashCalculation(String filePath) {
		this.filePath = filePath;
	}

}
