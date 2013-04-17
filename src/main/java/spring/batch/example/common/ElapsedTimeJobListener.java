package spring.batch.example.common;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class ElapsedTimeJobListener implements JobExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(ElapsedTimeJobListener.class);

    private StopWatch stopWatch = new StopWatch();

    public void beforeJob(JobExecution jobExecution) {
        logger.debug("Called beforeJob().");

        stopWatch.start();
    }

    public void afterJob(JobExecution jobExecution) {
        logger.debug("Called afterJob().");

        stopWatch.stop();
        logger.info("Elapsed time : " + DurationFormatUtils.formatDurationHMS(stopWatch.getTime()));
    }
}
