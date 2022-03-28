package com.ibm.checksumr.agent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChecksumrAgentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChecksumrAgentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}
