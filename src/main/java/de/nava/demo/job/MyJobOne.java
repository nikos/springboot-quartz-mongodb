package de.nava.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class MyJobOne extends QuartzJobBean {

    @Autowired
    private Environment env;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("This is Job 1, executed by {}", env);
    }
}