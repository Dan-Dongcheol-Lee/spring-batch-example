package spring.batch.example.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
