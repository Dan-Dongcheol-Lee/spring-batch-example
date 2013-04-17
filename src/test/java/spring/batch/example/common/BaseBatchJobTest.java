package spring.batch.example.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;

public abstract class BaseBatchJobTest {

    protected JobParameters getNextJobParameters(Job job, JobParameters jobParameters) {
        JobParametersIncrementer jobParametersIncrementer = job.getJobParametersIncrementer();
        if (jobParametersIncrementer != null) {
            return jobParametersIncrementer.getNext(jobParameters);
        }
        return jobParameters;
    }
}
