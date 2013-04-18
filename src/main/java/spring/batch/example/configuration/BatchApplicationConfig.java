package spring.batch.example.configuration;

import com.google.common.collect.Lists;
import com.mongodb.Mongo;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

@Configuration
@PropertySource("classpath:batch-oracle.properties")
public class BatchApplicationConfig {

	@Autowired
	private Environment environment;

	@Bean
	public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDatabaseType(DatabaseType.ORACLE.name());
        jobRepositoryFactoryBean.setDataSource(dataSource());
        jobRepositoryFactoryBean.setTransactionManager(transactionManager());
        return jobRepositoryFactoryBean.getJobRepository();
	}

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory().getObject());
	}

	@Bean
	public TaskExecutor asyncTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

    @Bean
    public TaskExecutor syncTaskExecutor() {
        return new SyncTaskExecutor();
    }
	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("batch.jdbc.driver"));
		dataSource.setUrl(environment.getProperty("batch.jdbc.url"));
		dataSource.setUsername(environment.getProperty("batch.jdbc.user"));
		dataSource.setPassword(environment.getProperty("batch.jdbc.password"));
		return dataSource;
	}

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(new Mongo("localhost", 27017), "test");
    }

    @PostConstruct
    private void init() {
        if (environment.getProperty("batch.execute.scripts", Boolean.class)) {
            DatabasePopulatorUtils.execute(resourceDatabasePopulator(), dataSource());
        }
    }
	
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setHibernateProperties(hibernateProperties());
        factory.setDataSource(dataSource());
        factory.setPackagesToScan(new String[]{"spring.batch.example.model"});
        return factory;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        hibernateProperties.put("hibernate.show_sql", "false");
        return hibernateProperties;
    }

    @Bean
    public ResourceDatabasePopulator resourceDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
       List<ClassPathResource> scripts = Lists.newArrayList(
               new ClassPathResource(environment.getProperty("batch.drop.script")),
               new ClassPathResource(environment.getProperty("batch.schema.script")),
               new ClassPathResource(environment.getProperty("batch.business.schema.script")));
        databasePopulator.setScripts(scripts.toArray(new ClassPathResource[]{}));
        return databasePopulator;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
