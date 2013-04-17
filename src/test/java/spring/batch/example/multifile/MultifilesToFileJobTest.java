package spring.batch.example.multifile;

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
@ContextConfiguration(locations = "classpath:spring/batch/example/multifile/MultifilesToFileJob.xml")
public class MultifilesToFileJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job multifilesToFileJob;
	
	@Test
	public void shouldSuccessfullyCompleteMultifilesToFileJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("READ_PATH", getProjectPath() + "/sample_data/multi/input/*/*.txt");
        jobParametersBuilder.addString("WRITE_FILE",
                getProjectPath() + "/sample_data/multi/output/person_multi_output.txt");

        JobParameters jobParameters = getNextJobParameters(multifilesToFileJob, jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(multifilesToFileJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
