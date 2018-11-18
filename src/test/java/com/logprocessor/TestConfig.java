package com.logprocessor;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.JsonRecordSeparatorPolicy;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.logprocessor.listener.LogMessageJsonLineMapper;
import com.logprocessor.model.LogMessage;

@Configuration
@ComponentScan("com.logprocessor")
public class TestConfig {
	
	@Value("${file.path}")
	public String filePath;

	@Bean
	public JobLauncherTestUtils jobLauncherTestUtils()
	{
		return new JobLauncherTestUtils();
	}
	
	@Bean
	public FlatFileItemReader<LogMessage> reader() {
		
		System.out.println("Test Bean :::::::::::::::::");
		return new FlatFileItemReaderBuilder<LogMessage>().name("logMessageItemReader")
				.resource(new ClassPathResource(filePath))
				.lineMapper(new LogMessageJsonLineMapper())
				.recordSeparatorPolicy(new JsonRecordSeparatorPolicy())
				.build();
	}

}
