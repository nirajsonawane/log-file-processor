package com.logprocessor;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.logprocessor.config.BatchConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BatchConfiguration.class,TestConfig.class })
@TestPropertySource(locations = "classpath:multi_line_test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ShouldProcess_MultilineJsonMessage_ValidMessageTest {
	
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
	private TestUtil testUtil;
	
	@Test
	public void jobShouldProcess_MultilineJsonMessage() throws Exception {
			
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();		
		Assert.assertEquals(ExitStatus.COMPLETED,jobExecution.getExitStatus() );
		Assert.assertEquals(2, testUtil.getMessageCount());
		Assert.assertEquals(1, testUtil.getReportCount());

	}

}
