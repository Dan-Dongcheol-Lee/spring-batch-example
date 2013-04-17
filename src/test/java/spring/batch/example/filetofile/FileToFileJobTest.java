package spring.batch.example.filetofile;

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
@ContextConfiguration(locations = "classpath:spring/batch/example/filetofile/FileToFileJob.xml")
public class FileToFileJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job fileToFileJob;
	
	@Test
	public void shouldSuccessfullyCompleteFileToFileJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);
        jobParametersBuilder.addString("READ_FILE", getProjectPath() + "/sample_data/person_create.txt");
        jobParametersBuilder.addString("WRITE_FILE", getProjectPath() + "/sample_data/person_after.txt");

        JobParameters jobParameters = getNextJobParameters(fileToFileJob, jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(fileToFileJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
