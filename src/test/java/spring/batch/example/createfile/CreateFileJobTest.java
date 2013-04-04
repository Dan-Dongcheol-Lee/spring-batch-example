package spring.batch.example.createfile;

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
import spring.batch.example.utils.PathUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/createfile/CreateFileJob.xml")
public class CreateFileJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job createFileJob;
	
	@Test
	public void shouldSuccessfullyCompleteCreateFileJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("FILE_PATH", PathUtils.getProjectPath() + "/sample_data/person_create.txt");
		jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
		jobParametersBuilder.addLong("LINE_COUNT", 55L);
		jobParametersBuilder.addString("APPEND_LINES", "false");
		
		JobExecution jobExecution = jobLauncher.run(createFileJob, jobParametersBuilder.toJobParameters());

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
