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
import com.logprocessor.model.LogMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchConfiguration.class, TestConfig.class })
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ShouldCorrecltyMapLogMessageFromFileToJavaObjectTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private TestUtil testUtil;
	
	
	
	
	

	@Test
	public void ShouldCorrecltyMapLogMessageFromFileToJavaObject() throws Exception {

		JobExecution jobExecution = jobLauncherTestUtils.launchJob();		
		Assert.assertEquals(ExitStatus.COMPLETED,jobExecution.getExitStatus() );
		
		LogMessage messageByIdAndState = testUtil.getMessageByIdAndState("scsmbstgra", "STARTED");

		Assert.assertEquals("scsmbstgra", messageByIdAndState.getId());
		Assert.assertEquals("STARTED", messageByIdAndState.getState());		
		Assert.assertEquals("12345", messageByIdAndState.getHost());
		Assert.assertEquals("1491377495212", messageByIdAndState.getTimestamp());
		

	}

}
