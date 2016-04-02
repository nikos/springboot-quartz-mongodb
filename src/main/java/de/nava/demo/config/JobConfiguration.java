package de.nava.demo.config;

import de.nava.demo.job.MyJobOne;
import de.nava.demo.job.MyJobTwo;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class JobConfiguration {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @PostConstruct
    private void initialize() throws Exception {
        schedulerFactoryBean.getScheduler().addJob(jobDetailMyJobOne(), true, true);
        if (!schedulerFactoryBean.getScheduler().checkExists(new TriggerKey("trigger1", "mygroup"))) {
            schedulerFactoryBean.getScheduler().scheduleJob(simpleTriggerMyJobOne());
        }

        schedulerFactoryBean.getScheduler().addJob(jobDetailMyJobTwo(), true, true);
        if (!schedulerFactoryBean.getScheduler().checkExists(new TriggerKey("trigger2", "mygroup"))) {
            schedulerFactoryBean.getScheduler().scheduleJob(cronTriggerMyJobTwo());
        }
    }

    private static JobDetail jobDetailMyJobOne() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey("jobone", "mygroup"));
        jobDetail.setJobClass(MyJobOne.class);
        // remain stored in the job store even if no triggers point to it anymore
        jobDetail.setDurability(true);
        return jobDetail;
    }

    private static Trigger simpleTriggerMyJobOne() {
        return newTrigger()
                .forJob(jobDetailMyJobOne())
                .withIdentity("trigger1", "mygroup")
                .withPriority(50)
                // Job is scheduled for 3+1 times with the interval of 30 seconds
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(30)
                        .withRepeatCount(3))
                .startAt(DateTime.now().plusSeconds(3).toDate())
                .build();
    }

    // ~~

    private static JobDetail jobDetailMyJobTwo() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey("jobtwo", "mygroup"));
        jobDetail.setJobClass(MyJobTwo.class);
        jobDetail.setDurability(true);
        JobDataMap map = new JobDataMap();
        map.put("name", "HaHa");
        map.put(MyJobTwo.COUNT, 1);
        jobDetail.setJobDataMap(map);
        return jobDetail;
    }

    private static Trigger cronTriggerMyJobTwo() {
        return newTrigger()
                .forJob(jobDetailMyJobTwo())
                .withIdentity("trigger2", "mygroup")
                .withPriority(100)
                // Job is scheduled for every 1 minute
                .withSchedule(cronSchedule("0 0/1 * 1/1 * ? *"))
                .startAt(DateTime.now().plusSeconds(3).toDate())
                .build();
    }

}