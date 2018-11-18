package com.logprocessor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PopulateReportTasklet implements Tasklet {

	private static final Logger log = LoggerFactory.getLogger(PopulateReportTasklet.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${event.alert.threshold}")
	private String eventAlertThreshold;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		String sql = "CREATE TABLE LONG_RUNING_MESSAGE (ID, EVENT_TYPE,HOST,EVENT_DURATION,ALERT) AS (SELECT \r\n"
				+ "	 A.ID AS ID, \r\n" + "	 A.EVENT_TYPE AS EVENT_TYPE ,\r\n" + "	 A.HOST  AS HOST,\r\n"
				+ "	 (B.EVENT_TIME_STAMP - A.EVENT_TIME_STAMP) AS EVENT_DURATION ,\r\n"
				+ "	 CASE WHEN Abs( B.EVENT_TIME_STAMP - A.EVENT_TIME_STAMP) >= ?  \r\n"
				+ "          THEN 'TRUE' ELSE 'FALSE' \r\n" + "       END AS ALERT\r\n"
				+ "	 FROM \"PUBLIC\".\"LOG_MESSAGES\" A ,\"PUBLIC\".\"LOG_MESSAGES\" B  WHERE A.ID=B.ID AND  A.STATE='STARTED' AND B.STATE='FINISHED') WITH DATA";

		log.debug("Populating Report SQL  {}", sql);
		jdbcTemplate.execute(sql.replace("?", eventAlertThreshold));

		/*
		 * jdbcTemplate.execute(sql,new PreparedStatementCallback<Boolean>(){
		 * 
		 * @Override public Boolean doInPreparedStatement(PreparedStatement ps) throws
		 * SQLException { ps.setString(1,"4"); return ps.execute(); } });
		 */

		return null;

	}

}
