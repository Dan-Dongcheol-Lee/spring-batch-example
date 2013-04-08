package spring.batch.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CommonJobListener implements JobExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(CommonJobListener.class);

    public void beforeJob(JobExecution jobExecution) {
        logger.debug("Called beforeJob().");
    }

    public void afterJob(JobExecution jobExecution) {
        logger.debug("Called afterJob().");
    }
}
