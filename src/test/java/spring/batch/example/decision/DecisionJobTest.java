package spring.batch.example.decision;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import spring.batch.example.configuration.BatchApplicationConfig;
import spring.batch.example.configuration.BatchItemConfig;
import spring.batch.example.createfile.CreateFileJobConfig;
import spring.batch.example.filetofile.FileToFileJobConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchApplicationConfig.class, BatchItemConfig.class,
        CreateFileJobConfig.class, FileToFileJobConfig.class, DecisionJobConfig.class },
loader = AnnotationConfigContextLoader.class)
public class DecisionJobTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
    @Qualifier("decisionJob1")
	private Job decisionJob1;

    @Autowired
    @Qualifier("decisionJob2")
    private Job decisionJob2;
	
	@Test
	public void shouldSuccessfullyCompleteDecisionJob1Flow1() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_1");
		jobParametersBuilder.addString("FILE_PATH",
						"D:/app_pilot/scala_workspaces/seeding-spring-batch/sample_data/person_create.txt");
		jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
		jobParametersBuilder.addLong("LINE_COUNT", 55L);
		jobParametersBuilder.addString("APPEND_LINES", "false");

		JobExecution jobExecution = jobLauncher.run(decisionJob1, jobParametersBuilder.toJobParameters());
		
		System.out.println(jobExecution);
	}

    @Test
    public void shouldSuccessfullyCompleteDecisionJob1Flow2() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_2");
        jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);

        JobExecution jobExecution = jobLauncher.run(decisionJob1, jobParametersBuilder.toJobParameters());

        System.out.println(jobExecution);
    }

    @Test
    public void shouldSuccessfullyCompleteDecisionJob2Flow1() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_1");
        jobParametersBuilder.addString("FILE_PATH",
                "D:/app_pilot/scala_workspaces/seeding-spring-batch/sample_data/person_create.txt");
        jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
        jobParametersBuilder.addLong("LINE_COUNT", 55L);
        jobParametersBuilder.addString("APPEND_LINES", "false");

        JobExecution jobExecution = jobLauncher.run(decisionJob2, jobParametersBuilder.toJobParameters());

        System.out.println(jobExecution);
    }

    @Test
    public void shouldSuccessfullyCompleteDecisionJob2Flow2() throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("FLOW_FLAG", "FLOW_2");
        jobParametersBuilder.addString("FILE_PATH",
                "D:/app_pilot/scala_workspaces/seeding-spring-batch/sample_data/person_create.txt");
        jobParametersBuilder.addString("FORMAT", "%s, %s, %d, %s, \"%s\"");
        jobParametersBuilder.addLong("LINE_COUNT", 55L);
        jobParametersBuilder.addString("APPEND_LINES", "false");
        jobParametersBuilder.addLong("COMMIT_INTERVAL", 10L);

        JobExecution jobExecution = jobLauncher.run(decisionJob2, jobParametersBuilder.toJobParameters());

        System.out.println(jobExecution);
    }
}
