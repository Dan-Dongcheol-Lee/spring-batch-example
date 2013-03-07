package spring.batch.example.configuration;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Date;
import java.util.Properties;

@Configuration
public class BatchSchedulerConfig {

    @Bean
    public Scheduler scheduler() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setQuartzProperties(quartzProperties());
        factoryBean.setJobDetails(jobDetails());
//        factoryBean.setTriggers(triggers());
        return factoryBean.getObject();
    }

    private JobDetail[] jobDetails() {
        JobDetail[] jobDetails = new JobDetail[] {
            JobBuilder.newJob()
                    .withIdentity("job1")
                    .usingJobData("testkey1", "testvalue1")
                    .build()
        };
        return jobDetails;
    }

    @Bean
    public Properties quartzProperties() {
        Properties properties = new Properties();
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        return properties;
    }

}
