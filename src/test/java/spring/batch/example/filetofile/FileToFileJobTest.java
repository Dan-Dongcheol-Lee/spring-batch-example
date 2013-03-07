package spring.batch.example.filetofile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import spring.batch.example.configuration.BatchApplicationConfig;
import spring.batch.example.configuration.BatchItemConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchApplicationConfig.class, BatchItemConfig.class, FileToFileJobConfig.class },
loader = AnnotationConfigContextLoader.class)
public class FileToFileJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Test
	public void shouldSuccessfullyCompleteAJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);

		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		
		System.out.println(jobExecution);
	}
}
