package spring.batch.example.partitioning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimaryKeyRangePartitioner implements Partitioner {

    private final static Logger logger = LoggerFactory.getLogger(PrimaryKeyRangePartitioner.class);

    private final JdbcTemplate jdbcTemplate;
    private final String query;

    public PrimaryKeyRangePartitioner(JdbcTemplate jdbcTemplate, String query) {
        this.jdbcTemplate = jdbcTemplate;
        this.query = query;
    }

    public Map<String, ExecutionContext> partition(int gridSize) {

        List<Map<String,Object>> pkRanges = jdbcTemplate.queryForList(
                query, gridSize, gridSize);

        if(pkRanges == null || pkRanges.isEmpty()) {
            pkRanges = Collections.EMPTY_LIST;
        }

        logger.info("pkRanges = {}", pkRanges);

		/*
		 * Create the minValue and maxValue according to PK lists.
		 * First partition step will use only maxValue whereas, the last one will use only minValue.
		 * For example,
         * partition0={rn=20, maxId=and id <= 2080669},
         * partition1={rn=40, minId=and id > 2080669, maxId=and id <= 2080689},
         * partition2={rn=60, minId=and id > 2080689, maxId=and id <= 2080709},
         * partition3={rn=80, minId=and id > 2080709, maxId=and id <= 2080729},
         * partition4={rn=100, minId=and id > 2080729}
		 */
        Map<String, ExecutionContext> partitionedStepExecutionContexts = new HashMap<>();
        int size = pkRanges.size();
        int start = 0;

        while (start < size) {
            ExecutionContext executionContext = new ExecutionContext();
            partitionedStepExecutionContexts.put("partition" + start, executionContext);

            if(start > 0) {
                executionContext.put("minId", "and id > " + pkRanges.get(start - 1).get("id"));
                executionContext.put("rn", pkRanges.get(start).get("rn"));
            }
            if(start + 1 < size) {
                executionContext.put("maxId", "and id <= " + pkRanges.get(start).get("id"));
                executionContext.put("rn", pkRanges.get(start).get("rn"));
            }
            start++;
        }

        logger.info("* partitioning result : {}", partitionedStepExecutionContexts);
        return partitionedStepExecutionContexts;
    }
}
