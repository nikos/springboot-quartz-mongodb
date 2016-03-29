package de.nava.demo.config;

import de.nava.demo.job.MyJobOne;
import de.nava.demo.job.MyJobTwo;
import org.quartz.Trigger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {

    @Bean
    public JobDetailFactoryBean jobDetailMyJobOne() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyJobOne.class);
        jobDetailFactory.setGroup("mygroup");
        jobDetailFactory.setName("jobone");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean(name = "trigger1")
    public FactoryBean<? extends Trigger> simpleTriggerMyJobOne() {
        SimpleTriggerFactoryBean triggerFactory = new SimpleTriggerFactoryBean();
        triggerFactory.setJobDetail(jobDetailMyJobOne().getObject());
        triggerFactory.setGroup("mygroup");
        triggerFactory.setName("trigger1");
        triggerFactory.setPriority(50);
        // Job is scheduled for 3+1 times with the interval of 30 seconds
        triggerFactory.setStartDelay(3000);
        triggerFactory.setRepeatInterval(30000);
        triggerFactory.setRepeatCount(3);
        return triggerFactory;
    }

    @Bean
    public JobDetailFactoryBean jobDetailMyJobTwo() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MyJobTwo.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "HaHa");
        map.put(MyJobTwo.COUNT, 1);
        jobDetailFactory.setJobDataAsMap(map);
        jobDetailFactory.setGroup("mygroup");
        jobDetailFactory.setName("jobtwo");
        jobDetailFactory.setDurability(true); //  remain stored in the job store even if no triggers point to it anymore
        return jobDetailFactory;
    }

    @Bean(name = "trigger2")
    public FactoryBean<? extends Trigger> cronTriggerMyJobTwo() {
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        triggerFactory.setJobDetail(jobDetailMyJobTwo().getObject());
        triggerFactory.setGroup("mygroup");
        triggerFactory.setName("trigger2");
        triggerFactory.setPriority(100);
        // Job is scheduled for every 1 minute
        triggerFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        triggerFactory.setStartDelay(3000);
        return triggerFactory;
    }

}