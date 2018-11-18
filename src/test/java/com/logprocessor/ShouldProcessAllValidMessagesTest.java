package com.logprocessor;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.logprocessor.config.BatchConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BatchConfiguration.class,TestConfig.class })
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ShouldProcessAllValidMessagesTest {
	
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
	private TestUtil testUtil;
	
	@Test
	public void jobShouldEndWithCompletedStatu_longRuningMessageTable_ShouldHaveAllData() throws Exception {
		

		JobExecution jobExecution = jobLauncherTestUtils.launchJob();		
		Assert.assertEquals(ExitStatus.COMPLETED,jobExecution.getExitStatus() );
		Assert.assertEquals(6, testUtil.getMessageCount());
		Assert.assertEquals(3, testUtil.getReportCount());

	}

}
