package spring.batch.example.configuration;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import spring.batch.example.common.ElapsedTimeJobListener;
import spring.batch.example.common.PersonItemProcessor;
import spring.batch.example.common.SequenceRunIdIncrementer;
import spring.batch.example.model.Person;

@Configuration
public abstract class BatchItemConfig {

    @Autowired
    private BatchApplicationConfig batchApplicationConfig;

    @Bean
    public SequenceRunIdIncrementer jobParametersIncrementer() {
        return new SequenceRunIdIncrementer(batchApplicationConfig.jdbcTemplate());
    }

    @Bean
    @Scope("prototype")
    public DefaultLineMapper<Person> personLineMapper() {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(personBeanWrapperFieldSetMapper());
        lineMapper.setLineTokenizer(personDelimitedLineTokenizer());
        return lineMapper;
    }

    @Bean
    @Scope("prototype")
    public DelimitedLineTokenizer personDelimitedLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(new String[]{"name", "surName", "age", "dateOfBirth", "address"});
        return tokenizer;
    }

    @Bean
    @Scope("prototype")
    public BeanWrapperFieldSetMapper<Person> personBeanWrapperFieldSetMapper() {
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);
        return fieldSetMapper;
    }

    @Bean
    @Scope("prototype")
    public FormatterLineAggregator<Person> personLineAggregator() {
        FormatterLineAggregator<Person> lineAggregator = new FormatterLineAggregator<>();
        lineAggregator.setFieldExtractor(personBeanWrapperFieldExtractor());
        lineAggregator.setFormat("%-5s, %-5s, %-5d, %-20s, %20s");
        return lineAggregator;
    }

    @Bean
    @Scope("prototype")
    public BeanWrapperFieldExtractor<Person> personBeanWrapperFieldExtractor() {
        BeanWrapperFieldExtractor<Person> fieldExtractor = new BeanWrapperFieldExtractor<Person>();
        fieldExtractor.setNames(new String[]{"surName", "name", "age", "address", "dateOfBirth"});
        return fieldExtractor;
    }

    @Bean
    public PersonItemProcessor personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public BeanPropertyRowMapper<Person> personBeanPropertyRowMapper() {
        return new BeanPropertyRowMapper<>(Person.class);
    }

    @Bean
    public ElapsedTimeJobListener elapsedTimeJobListener() {
        return new ElapsedTimeJobListener();
    }
}
