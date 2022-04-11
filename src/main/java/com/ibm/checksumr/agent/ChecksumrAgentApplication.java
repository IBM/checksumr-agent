package com.ibm.checksumr.agent;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChecksumrAgentApplication implements CommandLineRunner {
	private ScanReport report;

	public static void main(String[] args) {
		SpringApplication.run(ChecksumrAgentApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Set<MessageDigest> algorithms;
		HashComputer hashComputer;
		Path[] pathsToSearch;
		List<Path> filesToCheck;
		Path reportPath;
		DateTimeFormatter format;

		try {
			pathsToSearch = ScannableFilesManager.toPaths(args);
			filesToCheck = ScannableFilesManager.listFilepaths(pathsToSearch);

			algorithms = new HashSet<MessageDigest>();

			algorithms.add(MessageDigest.getInstance("MD5"));
			hashComputer = new HashComputer(algorithms);

			this.report = new ScanReport();
			for (Path file : filesToCheck) {
				report.getScannedFiles().add(hashComputer.calculate(file));
			}
			format = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd_HHmmss").appendLiteral(".json")
					.toFormatter();
			reportPath = Paths.get("report_" + this.report.getGenerationTime().format(format));

			FileWriter fw = new FileWriter(reportPath.toFile());
			fw.write(this.report.toJson());
			fw.close();

			System.out.println("Saved to " + reportPath.toAbsolutePath().toString());
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
