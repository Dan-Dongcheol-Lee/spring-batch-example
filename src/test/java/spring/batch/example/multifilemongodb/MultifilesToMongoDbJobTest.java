package spring.batch.example.multifilemongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static spring.batch.example.utils.PathUtils.getProjectPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/multifilemongodb/MultifilesToMongoDbJob.xml")
public class MultifilesToMongoDbJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job multifilesToMongoDbJob;
	
	@Test
	public void shouldSuccessfullyCompleteMultifilesToMongoDbJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("READ_PATH", getProjectPath() + "/sample_data/multi/input/*/*.txt");

		JobExecution jobExecution = jobLauncher.run(multifilesToMongoDbJob, jobParametersBuilder.toJobParameters());

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
