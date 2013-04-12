package spring.batch.example.filetodb;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import spring.batch.example.model.Person;

import static spring.batch.example.utils.PathUtils.getProjectPath;

//@Configuration
//@ImportResource("classpath:spring/batch/example/filetodb/FileToDbJob.xml")
public class FileToDbJobConfig {

    @Autowired
    private LineMapper personLineMapper;

    @Autowired
    private SessionFactory sessionFactory;

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
        HibernateItemWriter<Person> itemWriter = new HibernateItemWriter<>();
        itemWriter.setSessionFactory(sessionFactory);
        return itemWriter;
	}
}
