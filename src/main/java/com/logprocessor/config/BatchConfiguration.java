package com.logprocessor.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hsqldb.util.DatabaseManagerSwing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.JsonRecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.logprocessor.listener.JobCompletionNotificationListener;
import com.logprocessor.listener.LogMessageItemReadListener;
import com.logprocessor.listener.LogMessageJsonLineMapper;
import com.logprocessor.listener.LogMessageProcessorListener;
import com.logprocessor.model.LogMessage;
import com.logprocessor.processor.LogMessageItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${file.path}")
	public String filePath;

	@Value("${writer.sql}")
	public String writerSql;

	@Bean
	public FlatFileItemReader<LogMessage> reader() {
		log.info("Reading File from Path {}", filePath);
		return new FlatFileItemReaderBuilder<LogMessage>().name("logMessageItemReader")
				.resource(new FileSystemResource(filePath))
				.lineMapper(new LogMessageJsonLineMapper())
				.recordSeparatorPolicy(new JsonRecordSeparatorPolicy())
				.build();
	}

	@Bean
	public LogMessageItemProcessor processor() {
		return new LogMessageItemProcessor();
	}

	@Bean
	public Job importLogData(JobCompletionNotificationListener listener, Step step1, Step populateReport) {
		return jobBuilderFactory.get("importLogData")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.next(populateReport)
				.end()
				.build();
	}

	@Bean
	public Step step1(LogMessageProcessorListener processorlistener, LogMessageItemReadListener readListener,
			DataSource dataSource) {
		return stepBuilderFactory.get("step1")
				.<LogMessage, LogMessage>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer(dataSource))				
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(100)
				.listener(processorlistener)
				.listener(readListener)
				.build();
	}

	@Bean
	public JdbcBatchItemWriter<LogMessage> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<LogMessage>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql(writerSql)
				.dataSource(dataSource)
				.build();
	}

	@Bean
	public Step populateReport(PopulateReportTasklet tasklet) {
		return stepBuilderFactory.get("populateReport")
				.tasklet(tasklet)
				.build();
	}

	@PostConstruct
	public void getDbManager() {
		System.setProperty("java.awt.headless", "false");
		DatabaseManagerSwing.main(
				new String[] { "--url", "jdbc:hsqldb:file:db/log-messagees-db", "--user", "sa", "--password", "" });
	}

}
