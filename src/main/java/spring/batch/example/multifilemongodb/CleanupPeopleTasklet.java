package spring.batch.example.multifilemongodb;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import spring.batch.example.model.Person;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class CleanupPeopleTasklet implements Tasklet {
	
	private final static Logger logger = LoggerFactory.getLogger(CleanupPeopleTasklet.class);

    private MongoTemplate mongoTemplate;

    public CleanupPeopleTasklet(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.debug("Cleaning up people collection.");

        mongoTemplate.dropCollection("people");
		
		return RepeatStatus.FINISHED;
	}
}
