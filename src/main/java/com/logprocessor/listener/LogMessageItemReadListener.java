package com.logprocessor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.logprocessor.model.LogMessage;

@Component
public class LogMessageItemReadListener implements org.springframework.batch.core.ItemReadListener<LogMessage> {

	private static final Logger log = LoggerFactory.getLogger(LogMessageItemReadListener.class);

	@Override
	public void beforeRead() {
		
	}

	@Override
	public void afterRead(LogMessage item) {

	}

	@Override
	public void onReadError(Exception ex) {
		log.error(ex.getMessage());
	}

}
