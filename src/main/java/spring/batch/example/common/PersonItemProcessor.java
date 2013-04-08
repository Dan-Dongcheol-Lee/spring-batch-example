package spring.batch.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import spring.batch.example.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	private final static Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);
	
	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("Called beforeJob().");
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		logger.debug("Called beforeStep().");
	}
	
	@Override
	public Person process(Person item) throws Exception {
		logger.debug("process : " + item);
		return item;
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
