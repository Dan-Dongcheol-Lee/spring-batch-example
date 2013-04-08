package spring.batch.example.multifile;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class CleanupFileTasklet implements Tasklet {
	
	private final static Logger logger = LoggerFactory.getLogger(CleanupFileTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.debug("Cleaning up the target file.");
		
		Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
		String filePath = (String) jobParameters.get("WRITE_FILE");

        FileUtils.writeLines(new File((filePath)), Collections.EMPTY_LIST);
		
		return RepeatStatus.FINISHED;
	}
}
