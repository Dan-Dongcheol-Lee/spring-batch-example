package spring.batch.example.decision;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.batch.example.common.BaseBatchJobTest;
import spring.batch.example.utils.PathUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static spring.batch.example.utils.PathUtils.getProjectPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/decision/DecisionJob.xml")
public class DecisionJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
    @Qualifier("decisionJob1")
	private Job decisionJob1;

    @Autowired
    @Qualifier("decisionJob2")
    private Job decisionJob2;
	
	@Test
	public void shouldSuccessfullyCompleteDecisionJob1Flow1() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_1");
		jobParametersBuilder.addString("FILE_PATH", PathUtils.getProjectPath() + "/sample_data/person_create.txt");
		jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
		jobParametersBuilder.addLong("LINE_COUNT", 55L);
		jobParametersBuilder.addString("APPEND_LINES", "false");

        JobParameters jobParameters = getNextJobParameters(decisionJob1, jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(decisionJob1, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}

    @Test
    public void shouldSuccessfullyCompleteDecisionJob1Flow2() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_2");
        jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);
        jobParametersBuilder.addString("READ_FILE", getProjectPath() + "/sample_data/person_create.txt");
        jobParametersBuilder.addString("WRITE_FILE", getProjectPath() + "/sample_data/person_after.txt");

        JobParameters jobParameters = getNextJobParameters(decisionJob1, jobParametersBuilder.toJobParameters());

        JobExecution jobExecution = jobLauncher.run(decisionJob1, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
    }

    @Test
    public void shouldSuccessfullyCompleteDecisionJob2Flow1() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_1");
        jobParametersBuilder.addString("FILE_PATH", PathUtils.getProjectPath() + "/sample_data/person_create.txt");
        jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
        jobParametersBuilder.addLong("LINE_COUNT", 55L);
        jobParametersBuilder.addString("APPEND_LINES", "false");

        JobParameters jobParameters = getNextJobParameters(decisionJob2, jobParametersBuilder.toJobParameters());

        JobExecution jobExecution = jobLauncher.run(decisionJob2, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
    }

    @Test
    public void shouldSuccessfullyCompleteDecisionJob2Flow2() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_2");
        jobParametersBuilder.addString("FILE_PATH", PathUtils.getProjectPath() + "/sample_data/person_create.txt");
        jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
        jobParametersBuilder.addLong("LINE_COUNT", 55L);
        jobParametersBuilder.addString("APPEND_LINES", "false");
        jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);
        jobParametersBuilder.addString("READ_FILE", getProjectPath() + "/sample_data/person_create.txt");
        jobParametersBuilder.addString("WRITE_FILE", getProjectPath() + "/sample_data/person_after.txt");

        JobParameters jobParameters = getNextJobParameters(decisionJob2, jobParametersBuilder.toJobParameters());

        JobExecution jobExecution = jobLauncher.run(decisionJob2, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
    }
}
