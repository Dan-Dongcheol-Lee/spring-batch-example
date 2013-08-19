
select * from batch_job_execution;

select * from batch_job_instance;

select * from batch_step_execution order by step_execution_id desc;

select * from batch_step_execution_context order by step_execution_id desc;

select * from BATCH_JOB_EXECUTION_PARAMS order by job_execution_id desc;


select BATCH_RUN_ID_SEQ.nextval from dual;

select max(rn) from (select rownum as rn from person)
