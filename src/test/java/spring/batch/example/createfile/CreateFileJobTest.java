package spring.batch.example.createfile;

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
@ContextConfiguration(classes = { BatchApplicationConfig.class, BatchItemConfig.class, CreateFileJobConfig.class },
loader = AnnotationConfigContextLoader.class)
public class CreateFileJobTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Test
	public void shouldSuccessfullyCompleteAJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("FILE_PATH",
						"D:/app_pilot/scala_workspaces/seeding-spring-batch/sample_data/person_create.txt");
		//jobParametersBuilder.addString("FORMAT", "%-10s%-10s%10d%20s%-30s");
		jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
		jobParametersBuilder.addLong("LINE_COUNT", 55L);
		jobParametersBuilder.addString("APPEND_LINES", "false");
		
		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		
		System.out.println(jobExecution);
	}
}
