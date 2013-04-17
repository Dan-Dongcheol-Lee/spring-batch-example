package spring.batch.example.multifilemongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.batch.example.common.BaseBatchJobTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static spring.batch.example.utils.PathUtils.getProjectPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/multifilemongodb/MultifilesToMongoDbJob.xml")
public class MultifilesToMongoDbJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job multifilesToMongoDbJob;
	
	@Test
	public void shouldSuccessfullyCompleteMultifilesToMongoDbJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("READ_PATH", getProjectPath() + "/sample_data/multi/input/*/*.txt");

        JobParameters jobParameters = getNextJobParameters(multifilesToMongoDbJob,
                jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(multifilesToMongoDbJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
