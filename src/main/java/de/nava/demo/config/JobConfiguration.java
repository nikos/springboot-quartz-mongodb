package de.nava.demo.config;

import de.nava.demo.job.MyJobTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {

    @Autowired
    private SchedulerFactoryBean scheduler;

    @PostConstruct
    private void setUp() {
        // TODO: set triggers dynamically
        scheduler.setTriggers(
                // simpleTriggerMyJobOne,
                cronTriggerMyJobTwo().getObject()
        );
    }


    /* @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("jobone");
        obj.setTargetMethod("myTask");
        return obj;
    }

    // Job  is scheduled for 3+1 times with the interval of 30 seconds
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerMyJobOne() {
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setRepeatInterval(30000);
        stFactory.setRepeatCount(3);
        return stFactory;
    } */

    @Bean
    public JobDetailFactoryBean jobDetailMyJobTwo() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyJobTwo.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "HaHa");
        map.put(MyJobTwo.COUNT, 1);
        jobDetailFactory.setJobDataAsMap(map);
        jobDetailFactory.setGroup("mygroup");
        jobDetailFactory.setName("myjob");
        jobDetailFactory.setDurability(true); //  remain stored in the job store even if no triggers point to it anymore
        return jobDetailFactory;
    }

    // Job is scheduled for every 1 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerMyJobTwo() {
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        triggerFactory.setJobDetail(jobDetailMyJobTwo().getObject());
        triggerFactory.setStartDelay(3000);
        triggerFactory.setGroup("mygroup");
        triggerFactory.setName("mytrigger");
        triggerFactory.setPriority(100);
        triggerFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return triggerFactory;
    }

}