package spring.batch.example.decision;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Configuration
//@ImportResource("classpath:spring/batch/example/decision/DecisionJob.xml")
public class DecisionJobConfig {

    @Bean
    public DecisionJobListener decisionJobListener() {
        return new DecisionJobListener();
    }

    @Bean
    public StepFlowDecider stepFlowDecider() {
        return new StepFlowDecider();
    }
}
