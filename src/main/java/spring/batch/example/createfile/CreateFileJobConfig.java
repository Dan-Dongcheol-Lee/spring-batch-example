package spring.batch.example.createfile;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Configuration
//@ImportResource("classpath:spring/batch/example/createfile/CreateFileJob.xml")
public class CreateFileJobConfig {

	@Bean
	public Tasklet createFileTasklet() {
		return new CreateFileTasklet();
	}
}
