package de.nava.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJobTwo extends QuartzJobBean {

    public static final String COUNT = "count";

    private String name;

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
        int cnt = dataMap.getInt(COUNT);
        JobKey jobKey = ctx.getJobDetail().getKey();
        log.info("{}: {}: {}", jobKey, name, cnt);
        cnt++;
        dataMap.put(COUNT, cnt);
    }

    public void setName(String name) {
        this.name = name;
    }
} 