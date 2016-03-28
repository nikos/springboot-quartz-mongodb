package de.nava.demo.config;

import de.nava.demo.job.MyJobTwo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {

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
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(MyJobTwo.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "HaHa");
        map.put(MyJobTwo.COUNT, 1);
        factory.setJobDataAsMap(map);
        factory.setGroup("mygroup");
        factory.setName("myjob");
        factory.setDurability(true); //  remain stored in the job store even if no triggers point to it anymore
        return factory;
    }

    // Job is scheduled for every 1 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerMyJobTwo() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailMyJobTwo().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setGroup("mygroup");
        stFactory.setName("mytrigger");
        stFactory.setPriority(100);
        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

}