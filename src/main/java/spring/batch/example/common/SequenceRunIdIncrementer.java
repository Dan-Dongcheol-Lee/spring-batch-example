package spring.batch.example.common;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.jdbc.core.JdbcTemplate;

public class SequenceRunIdIncrementer implements JobParametersIncrementer {

    private JdbcTemplate jdbcTemplate;

    public SequenceRunIdIncrementer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JobParameters getNext(JobParameters parameters) {
        Long nextRunId = jdbcTemplate.queryForObject("select batch_run_id_seq.nextval from dual", Long.class);
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder(parameters);
        jobParametersBuilder.addLong("run.id", nextRunId);
        return jobParametersBuilder.toJobParameters();
    }
}
