package spring.batch.example.createfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.batch.example.common.BaseBatchJobTest;
import spring.batch.example.utils.PathUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/createfile/CreateFileJob.xml")
public class CreateFileJobTest extends BaseBatchJobTest {

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

        JobParameters jobParameters = getNextJobParameters(createFileJob, jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(createFileJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
