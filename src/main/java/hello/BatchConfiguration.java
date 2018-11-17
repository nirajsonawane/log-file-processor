package hello;

import javax.sql.DataSource;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<LogMessage> reader() {
		return new FlatFileItemReaderBuilder<LogMessage>().name("logMessageItemReader")
				.resource(new ClassPathResource("logData.txt"))
				.lineMapper(new LogMessageJsonLineMapper())
				.recordSeparatorPolicy(new JsonRecordSeparatorPolicy())
				.build();
	}

	@Bean
	public LogMessageItemProcessor processor() {
		return new LogMessageItemProcessor();
	}
	
	@Bean
	public Job importLogData(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importLogData")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
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
				.sql("INSERT INTO LOG_MESSAGES (ID,STATE, EVENT_TYPE,HOST,EVENT_TIME_STAMP) VALUES (:id, :state, :type ,:host,:timestamp)")
				.dataSource(dataSource)
				.build();
	}

}
