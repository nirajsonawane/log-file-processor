package com.logprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.logprocessor.model.BadRecordReport;

@Component
public class LogMessageProcessorDaoServiceImpl implements LogMessageProcessorDaoService {
	private static final Logger log = LoggerFactory.getLogger(LogMessageProcessorDaoServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	
	public void insertBadRecord(BadRecordReport badRecord) {	

		log.info("Inserting Error Data {} ", badRecord);
		String errorMessage = badRecord.getErrorMessage();
		if (errorMessage.length() > 100) {
			errorMessage = errorMessage.substring(0, 100);
		}
		String sql ="INSERT INTO BAD_RECORD_REPORT (LINE_NUMBER,ERROR_MESSAGE) VALUES(?,?)";	
		jdbcTemplate.update(sql,new Object[] { badRecord.getLineNumber(),errorMessage});
	

	}
}
