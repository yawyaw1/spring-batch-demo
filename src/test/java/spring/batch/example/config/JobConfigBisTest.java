package spring.batch.example.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import spring.batch.example.SpringBatchExampleApplication;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBatchExampleApplication.class)
public class JobConfigBisTest {

    @Autowired
    private Job job;

    @Autowired
    private Step step;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void should_launch_job() throws Exception {
        assertEquals("test-job", job.getName());
    }

    @Test
    public void should_launch_step() throws Exception {
        assertEquals("test-step", step.getName());
    }

}