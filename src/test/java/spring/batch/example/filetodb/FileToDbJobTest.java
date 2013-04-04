package spring.batch.example.filetodb;

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
@ContextConfiguration(locations = "classpath:spring/batch/example/filetodb/FileToDbJob.xml")
public class FileToDbJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job fileToDbJob;
	
	@Test
	public void shouldSuccessfullyCompleteFileToDbJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);
        jobParametersBuilder.addString("READ_FILE", getProjectPath() + "/sample_data/person_create.txt");

		JobExecution jobExecution = jobLauncher.run(fileToDbJob, jobParametersBuilder.toJobParameters());

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
