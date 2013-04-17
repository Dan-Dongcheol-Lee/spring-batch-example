package spring.batch.example.partitioning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class PersonRownumRangePartitioner implements Partitioner {

    private final static Logger logger = LoggerFactory.getLogger(PersonRownumRangePartitioner.class);

    private final JdbcTemplate jdbcTemplate;
    private final String query;

    public PersonRownumRangePartitioner(JdbcTemplate jdbcTemplate, String query) {
        this.jdbcTemplate = jdbcTemplate;
        this.query = query;
    }

    public Map<String, ExecutionContext> partition(int gridSize) {
        long min = 1L;
        Long max = jdbcTemplate.queryForObject(query, Long.class);
        if (max == null) {
            max = 1L;
        }
        return doPartition(min, max, gridSize);

    }

    protected Map<String, ExecutionContext> doPartition(long min, long max, int gridSize) {

        long targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> partitionedStepExecutionContexts = new HashMap<>();
        int number = 0;
        long start = min;
        long end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext stepExecutionContext = new ExecutionContext();
            partitionedStepExecutionContexts.put("partition_" + number, stepExecutionContext);
            if (end >= max) {
                end = max;
            }
            stepExecutionContext.putLong("partition_min", start);
            stepExecutionContext.putLong("partition_max", end);
            start += targetSize;
            end += targetSize;
            number++;
        }

        logger.debug("* partitioner : {}", partitionedStepExecutionContexts);
        return partitionedStepExecutionContexts;
    }
}
