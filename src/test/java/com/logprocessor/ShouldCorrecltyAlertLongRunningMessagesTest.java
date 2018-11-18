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
import com.logprocessor.model.LongRunningMessageReport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchConfiguration.class, TestConfig.class })
@TestPropertySource(locations = "classpath:long-running-test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ShouldCorrecltyAlertLongRunningMessagesTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private TestUtil testUtil;
	
	
	
	
	

	@Test
	public void ShouldCorrecltyMapLogMessageFromFileToJavaObject() throws Exception {

		JobExecution jobExecution = jobLauncherTestUtils.launchJob();		
		Assert.assertEquals(ExitStatus.COMPLETED,jobExecution.getExitStatus() );
		
		LongRunningMessageReport messageById1 = testUtil.getMessageByIdFromReport("1");
		LongRunningMessageReport messageById2 = testUtil.getMessageByIdFromReport("2");
		LongRunningMessageReport messageById3 = testUtil.getMessageByIdFromReport("3");
		Assert.assertEquals("TRUE", messageById1.getAlert().trim());
		Assert.assertEquals("TRUE", messageById2.getAlert().trim());
		Assert.assertEquals("FALSE", messageById3.getAlert().trim());

	}

}
