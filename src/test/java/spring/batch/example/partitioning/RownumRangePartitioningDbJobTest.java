package spring.batch.example.partitioning;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.batch.example.common.BaseBatchJobTest;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static spring.batch.example.utils.PathUtils.getProjectPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/batch/example/partitioning/RownumRangePartitioningDbJob.xml")
public class RownumRangePartitioningDbJobTest extends BaseBatchJobTest {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job rownumRangePartitioningDbJob;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() throws IOException {
        FileUtils.write(new File(getProjectPath() + "/sample_data/merged_person_output.txt"), "");
        jdbcTemplate.update("delete from person");

        String sql = "insert into person (id, name, sur_name, age, date_of_birth, address) " +
                "values (person_seq.nextval, '%s', '%s', %s, to_date('%s','DD/MM/YYYY'), '%s')";

        for (int i = 1; i <= 100; i++ ) {
            jdbcTemplate.update(String.format(sql, "Foo" + i, "Bar" + i, 10, "01/01/2000", "Melbourne, VIC"));
        }
    }

	@Test
	public void shouldSuccessfullyCompleteRownumRangePartitioningDbJob() throws Exception {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("WRITE_FILE", getProjectPath() + "/sample_data/merged_person_output.txt");

        JobParameters jobParameters = getNextJobParameters(rownumRangePartitioningDbJob,
                jobParametersBuilder.toJobParameters());

		JobExecution jobExecution = jobLauncher.run(rownumRangePartitioningDbJob, jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
	}
}
