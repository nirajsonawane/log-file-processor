package com.logprocessor;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		// validateInput(args);
		System.setProperty("file.path",
				"D:\\Niraj\\Programming\\log-file-processor\\src\\main\\resources\\logData.txt");
		SpringApplication.run(Application.class, args);
	}

	private static void validateInput(String[] args) {

		if (args.length != 1) {
			throw new InvalidParameterException("File Path is Needed");
		}

		String path = Optional.ofNullable(args[0])
				.orElseThrow(() -> new InvalidParameterException("File Path is Needed"));
		System.setProperty("file.path", path);
	}
}
