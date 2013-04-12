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

import static spring.batch.example.utils.PathUtils.getProjectPath;

//@Configuration
//@ImportResource("classpath:spring/batch/example/filetofile/FileToFileJob.xml")
public class FileToFileJobConfig {

    @Autowired
    private LineMapper personLineMapper;

    @Autowired
    private LineAggregator personLineAggregator;

	@Bean
	public ItemReader<Person> itemReader() {
		FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource(getProjectPath() + "/sample_data/person_create.txt"));
		itemReader.setComments(new String[] {"#"});
		itemReader.setLineMapper(personLineMapper);
		return itemReader;
	}

	@Bean
	public ItemWriter<Person> itemWriter() {
		FlatFileItemWriter<Person> itemWriter = new FlatFileItemWriter<>();
		itemWriter.setResource(new FileSystemResource(getProjectPath() + "/sample_data/person_after.txt"));
        itemWriter.setShouldDeleteIfExists(true);
		itemWriter.setLineAggregator(personLineAggregator);
		return itemWriter;
	}
}
