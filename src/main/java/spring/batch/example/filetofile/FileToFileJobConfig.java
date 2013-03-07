package spring.batch.example.filetofile;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.FileSystemResource;
import spring.batch.example.model.Person;

@Configuration
@ImportResource("classpath:spring/batch/example/filetofile/FileToFileJob.xml")
public class FileToFileJobConfig {

    @Autowired
    private LineMapper personLineMapper;

    @Autowired
    private LineAggregator personLineAggregator;

	@Bean
	public ItemReader<Person> itemReader() {
		String path = "D:/app_pilot/scala_workspaces/seeding-spring-batch/sample_data/person_create.txt";
		FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource(path));
		itemReader.setComments(new String[] {"#"});
		itemReader.setLineMapper(personLineMapper);
		return itemReader;
	}

	@Bean
	public ItemWriter<Person> itemWriter() {
		String path = "D:/app_pilot/scala_workspaces/seeding-spring-batch/sample_data/person_after.txt";
		FlatFileItemWriter<Person> itemWriter = new FlatFileItemWriter<>();
		itemWriter.setResource(new FileSystemResource(path));
        itemWriter.setShouldDeleteIfExists(true);
		itemWriter.setLineAggregator(personLineAggregator);
		return itemWriter;
	}
}
