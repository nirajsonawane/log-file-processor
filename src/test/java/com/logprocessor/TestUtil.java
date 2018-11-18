package com.logprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.logprocessor.model.LogMessage;
import com.logprocessor.model.LongRunningMessageReport;

@Component
public class TestUtil {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int getMessageCount() {
		return jdbcTemplate.queryForObject("SELECT count(*) FROM LOG_MESSAGES", Integer.class);
	}

	public int getReportCount() {
		return jdbcTemplate.queryForObject("SELECT count(*) FROM LONG_RUNING_MESSAGE", Integer.class);
	}
	
	public LogMessage getMessageByIdAndState(String id, String state) {
		return (LogMessage)jdbcTemplate.queryForObject("SELECT ID,STATE,EVENT_TYPE AS TYPE,HOST,EVENT_TIME_STAMP AS timestamp FROM LOG_MESSAGES where ID =? and STATE=?",
				new Object[] { id, state }, new BeanPropertyRowMapper(LogMessage.class));
	}
	public LongRunningMessageReport getMessageByIdFromReport(String id) {
		return (LongRunningMessageReport)jdbcTemplate.queryForObject("SELECT * FROM LONG_RUNING_MESSAGE where ID =? ",
				new Object[] { id }, new BeanPropertyRowMapper(LongRunningMessageReport.class));
	}

}
