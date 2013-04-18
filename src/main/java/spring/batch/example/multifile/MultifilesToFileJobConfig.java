package spring.batch.example.multifile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Configuration
//@ImportResource("classpath:spring/batch/example/multifile/PrimaryKeyRangePartitioningDbJob.xml")
public class MultifilesToFileJobConfig {

    @Bean
    public CleanupFileTasklet cleanupFileTasklet() {
        return new CleanupFileTasklet();
    }
}
