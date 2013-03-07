package spring.batch.example.filetodb;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.FileSystemResource;
import spring.batch.example.configuration.BatchApplicationConfig;
import spring.batch.example.configuration.BatchItemConfig;
import spring.batch.example.model.Person;

@Configuration
@ImportResource("classpath:spring/batch/example/filetodb/FileToDbJob.xml")
public class FileToDbJobConfig {

    @Autowired
    private LineMapper personLineMapper;

    @Autowired
    private SessionFactory sessionFactory;

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
        HibernateItemWriter<Person> itemWriter = new HibernateItemWriter<>();
        itemWriter.setSessionFactory(sessionFactory);
        return itemWriter;
	}
}
