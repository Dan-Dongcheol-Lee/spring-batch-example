package spring.batch.example.partitioning;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.batch.example.common.BaseBatchJobTest;
import spring.batch.example.model.Person;

import java.io.File;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static spring.batch.example.utils.PathUtils.getProjectPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/partitioning/PrimaryKeyRangePartitioningDbJob.xml")
public class PrimaryKeyRangePartitioningDbJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job primaryKeyRangePartitioningDbJob;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setup() throws Exception {
        File file = new File(getProjectPath() + "/sample_data/merged_person_output.txt");
        if (file.exists()) {
            FileUtils.write(file, "");
        }
        Session session = sessionFactory.openSession();
        session.createQuery("delete from Person").executeUpdate();

        Date date = DateUtils.parseDate("01/01/2000", "dd/MM/yyyy");
        for (int i = 1; i <= 20000; i++ ) {
            session.save(new Person("Foo" + i, "Bar" + i, 10, date, "Melbourne, VIC"));
        }
        session.flush();
        session.clear();
    }

	@Test
	public void shouldSuccessfullyCompletePrimaryKeyRangePartitioningDbJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("WRITE_PREFIX", getProjectPath() + "/sample_data/result/merged_person");
        jobParametersBuilder.addLong("COMMIT_INTERVAL", 2000L);

        JobParameters jobParameters = getNextJobParameters(primaryKeyRangePartitioningDbJob,
                jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(primaryKeyRangePartitioningDbJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
