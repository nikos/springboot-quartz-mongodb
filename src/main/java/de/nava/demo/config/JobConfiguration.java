package de.nava.demo.config;

import de.nava.demo.job.MyJobOne;
import de.nava.demo.job.MyJobTwo;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.HashSet;

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
        schedulerFactoryBean.getScheduler().scheduleJob(simpleTriggerMyJobOne());

        schedulerFactoryBean.getScheduler().addJob(jobDetailMyJobTwo(), true, true);
        schedulerFactoryBean.getScheduler().scheduleJob(cronTriggerMyJobTwo());
        /*
          schedulerFactoryBean.getScheduler().scheduleJob(jobDetailMyJobTwo(),
                new HashSet<>(Arrays.asList(cronTriggerMyJobTwo())), true);
          throws:
              caused by: java.lang.UnsupportedOperationException: null
	            at com.novemberain.quartz.mongodb.MongoDBJobStore.storeJobsAndTriggers(MongoDBJobStore.java:172) ~[quartz-mongodb-2.0.0-rc1.jar:na]
	            at org.quartz.core.QuartzScheduler.scheduleJobs(QuartzScheduler.java:1056) ~[quartz-2.2.2.jar:na]
        */
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