package com.logprocessor.listener;

import org.springframework.batch.item.file.LineMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logprocessor.exception.LogMessageProcessingException;
import com.logprocessor.model.LogMessage;

public class LogMessageJsonLineMapper implements LineMapper<LogMessage> {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public LogMessage mapLine(String line, int lineNumber) {
		try {
			LogMessage readValue = mapper.readValue(line, LogMessage.class);
			readValue.setLineNumber(new Long(lineNumber));
			return readValue;
		} catch (Exception e) {
			throw new LogMessageProcessingException("Invalid Json Object at line number " + lineNumber, e);
		}
	}

}