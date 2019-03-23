package spring.batch.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.batch.example.enums.ProjectConstants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBatchExampleApplication.class)
public class SpringBatchExampleApplicationTests {

	@Autowired
	private Job job;

	@Test
	public void contextLoads() {
		assertNotNull(job);
		assertEquals(ProjectConstants.JOB_NAME,job.getName());
	}
}
