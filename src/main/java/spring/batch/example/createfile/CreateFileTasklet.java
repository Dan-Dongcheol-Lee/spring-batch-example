package spring.batch.example.createfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CreateFileTasklet implements Tasklet {
	
	private final static Logger logger = LoggerFactory.getLogger(CreateFileTasklet.class);

	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("Called beforeJob().");
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		logger.debug("Called beforeStep().");
	}
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.debug("Called execute().");
		
		Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
		String filePath = (String) jobParameters.get("FILE_PATH");
		String format = (String) jobParameters.get("FORMAT");
		long lineCount = (Long) jobParameters.get("LINE_COUNT");
		boolean appendLines = Boolean.parseBoolean((String) jobParameters.get("APPEND_LINES"));
		
		File file = new File(filePath);

		List<String> lines = new ArrayList<>();
		for (int i = 0 ; i < lineCount ; i++) {
			lines.add(String.format(format, "Dan" + i, "Lee" + i, i, "01/03/2013", "10 Queens Rd, Melbourne, VIC"));
		}

		FileUtils.writeLines(file , lines, appendLines);
		
		return RepeatStatus.FINISHED;
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.debug("Called afterStep().");
		return null;
	}
	
	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		logger.debug("Called afterJob().");
	}
}
