package spring.batch.example.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
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
import spring.batch.example.configuration.BatchSchedulerConfig;
import spring.batch.example.filetofile.FileToFileJobConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BatchSchedulerConfig.class },
loader = AnnotationConfigContextLoader.class)
public class ScheduleJobTest {

	@Autowired
	private Scheduler scheduler;

	@Test
	public void shouldScheduleJobByTrigger() throws Exception {

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1")
                .forJob("job1")
                .usingJobData("testkey2", "testvalue2")
                .build();

        scheduler.scheduleJob(trigger);

        Thread.sleep(5000);
	}
}
