package hello;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class LogMessageItemProcessor implements ItemProcessor<LogMessage, LogMessage> {

	private static final Logger log = LoggerFactory.getLogger(LogMessageItemProcessor.class);

	private Validator validator;

	@PostConstruct
	private void setupValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();

	}

	@Override
	public LogMessage process(final LogMessage logMessage) throws Exception {

		Set<ConstraintViolation<LogMessage>> validation = validator.validate(logMessage);
		if(!validation.isEmpty())
		{
			 throw new LogMessageProcessingException("Mandetory parametr Missing at line number " +logMessage.getLineNumber());
		}
		log.info("Log Message {}", logMessage);
		return logMessage;
	}

}
