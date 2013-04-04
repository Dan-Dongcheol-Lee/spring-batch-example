package spring.batch.example.decision;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class StepFlowDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String FLOW_FLAG = jobExecution.getJobParameters().getString("FLOW_FLAG", "EXIT");
        return new FlowExecutionStatus(FLOW_FLAG);
    }
}
