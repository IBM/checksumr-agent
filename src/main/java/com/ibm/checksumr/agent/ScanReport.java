package com.ibm.checksumr.agent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class ScanReport {
	@Getter
	@Setter
	private LocalDateTime generationTime;

	@Getter
	@Setter
	private Set<FileHashCalculation> scannedFiles;

	public ScanReport() {
		this(LocalDateTime.now(), new HashSet<FileHashCalculation>());
	}

	public ScanReport(LocalDateTime generationTime) {
		this(generationTime, new HashSet<FileHashCalculation>());
	}

	public ScanReport(LocalDateTime generationTime, Set<FileHashCalculation> scannedFiles) {
		this.generationTime = generationTime;
		this.scannedFiles = scannedFiles;
	}

	public String toJson() {
		int i, j;
		String[] scannedFiles = new String[this.scannedFiles.size()];
		i = 0;
		for (FileHashCalculation scannedFile : this.scannedFiles) {
			String[] checksums = new String[scannedFile.getChecksums().size()];
			j = 0;
			for (Entry<String, String> checksum : scannedFile.getChecksums().entrySet()) {
				checksums[j] = "{\"" + checksum.getKey() + "\":\"" + checksum.getValue() + "\"}";
				j++;
			}
			scannedFiles[i] = "{\"filePath\":\"" + scannedFile.getFilePath().replace("\\", "/") + "\",\"checksums\":["
					+ String.join(",", checksums) + "]}";
			i++;
		}

		String json = "{\"generationTime\":\"" + this.generationTime.format(DateTimeFormatter.ISO_DATE_TIME) + "\","
				+ "\"scannedFiles\":[" + String.join(",", scannedFiles) + "]}";

		return json;
	}

}
