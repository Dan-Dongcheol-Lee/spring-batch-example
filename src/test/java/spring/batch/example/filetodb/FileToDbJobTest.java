package spring.batch.example.filetodb;

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
@ContextConfiguration(locations = "classpath:spring/batch/example/filetodb/FileToDbJob.xml")
public class FileToDbJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job fileToDbJob;
	
	@Test
	public void shouldSuccessfullyCompleteFileToDbJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);
        jobParametersBuilder.addString("READ_FILE", getProjectPath() + "/sample_data/person_create.txt");

        JobParameters jobParameters = getNextJobParameters(fileToDbJob, jobParametersBuilder.toJobParameters());

        JobExecution jobExecution = jobLauncher.run(fileToDbJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
