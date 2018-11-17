package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
public class LogMessageProcessorListener implements ItemProcessListener<LogMessage, LogMessage> {

	private static final Logger log = LoggerFactory.getLogger(LogMessageProcessorListener.class);

	@Override
	public void beforeProcess(LogMessage item) {
		
	}
	

	@Override
	public void afterProcess(LogMessage item, LogMessage result) {

	}

	@Override
	public void onProcessError(LogMessage item, Exception e) {
		log.error("Error at line number {} Error Message {}", item.getLineNumber(), e.getMessage());

	}

}
