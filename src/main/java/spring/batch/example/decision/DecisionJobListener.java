package spring.batch.example.decision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class DecisionJobListener {

    private final static Logger logger = LoggerFactory.getLogger(DecisionJobListener.class);

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        logger.debug("Called beforeJob().");
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        logger.debug("Called afterJob().");
    }
}

